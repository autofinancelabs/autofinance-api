package com.autofinance.api.creditsimulation.domain.model.aggregates;

import com.autofinance.api.creditsimulation.domain.exceptions.ScheduleNotBalancedException;
import com.autofinance.api.creditsimulation.domain.model.events.SimulationGenerated;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ClientId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Costs;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceConfiguration;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Indicators;
import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Percentage;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Rate;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationState;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationSummary;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Term;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import com.autofinance.api.creditsimulation.domain.services.IndicatorsCalculator;
import com.autofinance.api.creditsimulation.domain.services.ScheduleCalculator;
import com.autofinance.api.creditsimulation.domain.services.SummaryCalculator;
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
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.TenantId;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root of the Credit Simulation core: holds the configuration, the generated schedule,
 * the indicators and the accumulated summary, enforcing the balance invariant in a single transaction.
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

    /** Grace plan persisted as an ordered child table; wrapped in {@link GraceConfiguration} for calc. */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "grace_period", joinColumns = @JoinColumn(name = "credit_simulation_id"))
    @OrderColumn(name = "period_index")
    @Enumerated(EnumType.STRING)
    @Column(name = "grace_type")
    private List<GraceType> grace = new ArrayList<>();

    /** Flexible cost set persisted as an ordered child table; wrapped in {@link Costs} for calc. */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "credit_simulation_cost", joinColumns = @JoinColumn(name = "credit_simulation_id"))
    @OrderColumn(name = "cost_index")
    private List<Cost> costs = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "cost_of_capital_value")),
            @AttributeOverride(name = "type", column = @Column(name = "cost_of_capital_type")),
            @AttributeOverride(name = "capitalization", column = @Column(name = "cost_of_capital_capitalization"))
    })
    private Rate costOfCapital;

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

    /** Computed schedule persisted as a jsonb snapshot (the quote as generated). */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "schedule", columnDefinition = "jsonb")
    private List<ScheduleRow> schedule = new ArrayList<>();

    @Embedded
    private Indicators indicators;

    /** Accumulated totals persisted as a jsonb snapshot. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "summary", columnDefinition = "jsonb")
    private SimulationSummary summary;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private SimulationState state;

    @Version
    @Column(name = "version")
    private long version;

    protected CreditSimulation() {
        // for JPA
    }

    public CreditSimulation(SimulationId id, UUID dealershipId, ClientId clientId, VehicleOfferId vehicleOfferId,
                            Money salePrice, Rate rate, Percentage initialPercentage, Percentage balloonPercentage,
                            Term term, GraceConfiguration grace, Costs costs, Rate costOfCapital) {
        this.id = id;
        this.dealershipId = dealershipId;
        this.clientId = clientId;
        this.vehicleOfferId = vehicleOfferId;
        this.salePrice = salePrice;
        this.rate = rate;
        this.initialPercentage = initialPercentage;
        this.balloonPercentage = balloonPercentage;
        this.term = term;
        this.grace = new ArrayList<>(grace.periods());
        this.costs = new ArrayList<>(costs.items());
        this.costOfCapital = costOfCapital;

        BigDecimal i = rate.toPeriodicRate(term.frequencyDays(), term.daysPerYear());
        BigDecimal downPayment = initialPercentage.of(salePrice.amount());
        BigDecimal loan = salePrice.amount().subtract(downPayment, FinancialMath.MC)
                .add(costs.initialTotal(), FinancialMath.MC);
        this.loanAmount = new Money(loan, salePrice.currency());

        BigDecimal balloon = balloonPercentage.of(salePrice.amount());
        BigDecimal balloonRate = i.add(costs.embeddedRate(term.frequencyDays()), FinancialMath.MC);
        BigDecimal presentValueOfBalloon = ScheduleCalculator.balloonPresentValue(
                balloon, balloonRate, term.numberOfInstallments());
        this.financedBalance = new Money(loan.subtract(presentValueOfBalloon, FinancialMath.MC), salePrice.currency());

        this.state = SimulationState.CONFIGURED;
    }

    /** Builds the schedule, indicators and summary (double-dispatch), verifies balance, transitions state. */
    public void generate(ScheduleCalculator scheduleCalculator, IndicatorsCalculator indicatorsCalculator,
                         SummaryCalculator summaryCalculator) {
        BigDecimal i = rate.toPeriodicRate(term.frequencyDays(), term.daysPerYear());
        BigDecimal balloon = balloonPercentage.of(salePrice.amount());

        this.schedule = scheduleCalculator.build(
                loanAmount.amount(), balloon, i, salePrice.amount(), term, graceConfiguration(), costSet());

        BigDecimal periodicCostOfCapital = costOfCapital.toPeriodicRate(term.frequencyDays(), term.daysPerYear());
        BigDecimal effectiveAnnualRate = rate.toEffectiveAnnual(term.daysPerYear());
        this.indicators = indicatorsCalculator.compute(
                loanAmount.amount(), schedule, periodicCostOfCapital, term.installmentsPerYear(),
                effectiveAnnualRate, i);
        this.summary = summaryCalculator.compute(schedule);

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

    /** Wraps the persisted raw grace list into the domain value object used by the calculators. */
    private GraceConfiguration graceConfiguration() {
        return new GraceConfiguration(grace);
    }

    /** Wraps the persisted raw cost list into the domain value object used by the calculators. */
    private Costs costSet() {
        return new Costs(costs);
    }

    @Override
    public SimulationId getId() {
        return id;
    }
}
