package com.autofinance.api.creditsimulation.domain.model.aggregates;

import com.autofinance.api.creditsimulation.domain.exceptions.ScheduleNotBalancedException;
import com.autofinance.api.creditsimulation.domain.model.events.SimulationGenerated;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ClientId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.DealershipId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceConfiguration;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Indicators;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.InitialCosts;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Money;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.PeriodicCosts;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Percentage;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Rate;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationState;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Term;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import com.autofinance.api.creditsimulation.domain.services.IndicatorsCalculator;
import com.autofinance.api.creditsimulation.domain.services.ScheduleCalculator;
import com.autofinance.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Transient;
import lombok.Getter;
import org.hibernate.annotations.TenantId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root of the Credit Simulation core: holds the configuration, the generated schedule
 * and the indicators, enforcing the balance invariant in a single transaction.
 */
@Getter
@Entity
public class CreditSimulation extends AuditableAbstractAggregateRoot<CreditSimulation, SimulationId> {

    private static final BigDecimal BALANCE_TOLERANCE = new BigDecimal("0.10");

    @EmbeddedId
    private SimulationId id;

    @TenantId
    @Column(name = "dealership_id")
    private UUID dealershipId;

    @Embedded
    private ClientId clientId;

    @Embedded
    private VehicleOfferId vehicleOfferId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "sale_price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "sale_price_currency"))
    })
    private Money salePrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "rate_value")),
            @AttributeOverride(name = "type", column = @Column(name = "rate_type")),
            @AttributeOverride(name = "capitalization", column = @Column(name = "rate_capitalization"))
    })
    private Rate rate;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "initial_percentage"))
    private Percentage initialPercentage;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "balloon_percentage"))
    private Percentage balloonPercentage;

    @Embedded
    private Term term;

    /** Persistence mapping (grace_period child table) is deferred to the persistence slice. */
    @Transient
    private GraceConfiguration grace;

    @Embedded
    private InitialCosts initialCosts;

    @Embedded
    private PeriodicCosts periodicCosts;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "cost_of_capital_value")),
            @AttributeOverride(name = "type", column = @Column(name = "cost_of_capital_type")),
            @AttributeOverride(name = "capitalization", column = @Column(name = "cost_of_capital_capitalization"))
    })
    private Rate costOfCapital;

    @Column(name = "desgravamen_embebido")
    private boolean desgravamenEmbebido;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "loan_amount_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "loan_amount_currency"))
    })
    private Money loanAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "financed_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "financed_balance_currency"))
    })
    private Money financedBalance;

    @ElementCollection
    @CollectionTable(name = "schedule_row", joinColumns = @JoinColumn(name = "credit_simulation_id"))
    @OrderColumn(name = "row_index")
    private List<ScheduleRow> schedule = new ArrayList<>();

    @Embedded
    private Indicators indicators;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private SimulationState state;

    protected CreditSimulation() {
        // for JPA
    }

    public CreditSimulation(SimulationId id, UUID dealershipId, ClientId clientId, VehicleOfferId vehicleOfferId,
                            Money salePrice, Rate rate, Percentage initialPercentage, Percentage balloonPercentage,
                            Term term, GraceConfiguration grace, InitialCosts initialCosts, PeriodicCosts periodicCosts,
                            Rate costOfCapital, boolean desgravamenEmbebido) {
        this.id = id;
        this.dealershipId = dealershipId;
        this.clientId = clientId;
        this.vehicleOfferId = vehicleOfferId;
        this.salePrice = salePrice;
        this.rate = rate;
        this.initialPercentage = initialPercentage;
        this.balloonPercentage = balloonPercentage;
        this.term = term;
        this.grace = grace;
        this.initialCosts = initialCosts;
        this.periodicCosts = periodicCosts;
        this.costOfCapital = costOfCapital;
        this.desgravamenEmbebido = desgravamenEmbebido;

        BigDecimal i = rate.toPeriodicRate(term.frequencyDays(), term.daysPerYear());
        BigDecimal downPayment = initialPercentage.of(salePrice.amount());
        BigDecimal loan = salePrice.amount().subtract(downPayment, FinancialMath.MC)
                .add(initialCosts.total(), FinancialMath.MC);
        this.loanAmount = new Money(loan, salePrice.currency());

        BigDecimal cuoton = balloonPercentage.of(salePrice.amount());
        BigDecimal presentValueOfBalloon = cuoton.signum() > 0
                ? cuoton.divide(FinancialMath.pow(BigDecimal.ONE.add(i), term.numberOfInstallments()), FinancialMath.MC)
                : BigDecimal.ZERO;
        this.financedBalance = new Money(loan.subtract(presentValueOfBalloon, FinancialMath.MC), salePrice.currency());

        this.state = SimulationState.CONFIGURED;
    }

    /** Builds the schedule and indicators (double-dispatch), verifies the balance, transitions state. */
    public void generate(ScheduleCalculator scheduleCalculator, IndicatorsCalculator indicatorsCalculator) {
        BigDecimal i = rate.toPeriodicRate(term.frequencyDays(), term.daysPerYear());
        BigDecimal cuoton = balloonPercentage.of(salePrice.amount());

        this.schedule = scheduleCalculator.build(
                loanAmount.amount(), cuoton, i, term, grace, periodicCosts, desgravamenEmbebido);

        BigDecimal periodicCostOfCapital = costOfCapital.toPeriodicRate(term.frequencyDays(), term.daysPerYear());
        BigDecimal effectiveAnnualRate = rate.toEffectiveAnnual(term.daysPerYear());
        this.indicators = indicatorsCalculator.compute(
                loanAmount.amount(), schedule, periodicCostOfCapital, term.installmentsPerYear(),
                effectiveAnnualRate, i);

        verifyBalance();
        this.state = SimulationState.GENERATED;
        addDomainEvent(new SimulationGenerated(id, new DealershipId(dealershipId), schedule.size()));
    }

    private void verifyBalance() {
        int n = term.numberOfInstallments();
        BigDecimal lastRegularClosing = schedule.stream()
                .filter(row -> row.period() == n)
                .map(ScheduleRow::closingBalance)
                .findFirst()
                .orElseThrow(() -> new ScheduleNotBalancedException("Missing final ordinary period " + n));
        if (lastRegularClosing.abs().compareTo(BALANCE_TOLERANCE) > 0) {
            throw new ScheduleNotBalancedException(
                    "Last regular closing balance is not ~0: " + lastRegularClosing);
        }
    }

    @Override
    public SimulationId getId() {
        return id;
    }
}
