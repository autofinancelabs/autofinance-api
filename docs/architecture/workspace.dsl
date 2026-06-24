workspace "AutoFinance" "Financiamiento vehicular en Perú: configura un crédito y genera el plan de pagos (método francés + Compra Inteligente) e indicadores de transparencia." {

    model {
        advisor = person "Asesor de crédito" "Rol operativo de la entidad financiera que registra cliente/oferta, configura el financiamiento y genera simulaciones."

        idp = softwareSystem "Identity Provider" "Provee identidad y sesión (p. ej. Spring Security). El negocio lo consume tal cual (Conformist)." {
            tags "external"
        }

        autofinance = softwareSystem "AutoFinance API" "Configura financiamientos vehiculares y produce el cronograma e indicadores VAN/TIR/TCEA desde la perspectiva de la entidad." {

            api = container "REST API" "Expone los casos de uso; aloja los 4 bounded contexts en 4 capas DDD (interfaces, application, domain, infrastructure)." "Spring Boot 4.1 / Java 25" {

                # --- Core: Credit Simulation (4 capas) ---
                simController  = component "Simulations Controller" "interfaces/rest — recibe los comandos del asesor." "Spring MVC"
                simAppService  = component "Simulation Command/Query Service" "application — orquesta el caso de uso, sin reglas de negocio." "Spring @Service"
                simAggregate   = component "CreditSimulation Aggregate" "domain — raíz: configuración + cronograma + indicadores; enforce de invariantes." "DDD aggregate root" {
                    tags "core"
                }
                scheduleCalc   = component "ScheduleCalculator" "domain service — construye las n filas (francés/balloon + gracia + bloque cuotón + liquidación)." {
                    tags "core"
                }
                indicatorsCalc = component "IndicatorsCalculator" "domain service — VAN/TIR/TCEA + COK del periodo + eco de tasas." {
                    tags "core"
                }
                simFactory     = component "CreditSimulationFactory" "domain — ensambla una configuración válida y dispara el cálculo inicial."
                simRepoPort    = component "CreditSimulationRepository" "domain — port de persistencia (uno por raíz)."
                simRepoJpa     = component "JPA CreditSimulation Repository" "infrastructure/persistence/jpa — implementa el port." "Spring Data JPA"

                # --- Supporting / generic ---
                clientsComp    = component "Clients Component" "Client (supporting) — CRUD + repositorio."
                offersComp     = component "Vehicle Offers Component" "VehicleOffer (supporting) — CRUD + repositorio."
                iamComp        = component "Identity & Access Component" "User/Session (generic) — Conformist; delega en el Identity Provider."

                # Wiring del core (dependencias hacia adentro)
                simController -> simAppService "invoca casos de uso"
                simAppService -> simFactory "crea la simulación"
                simAppService -> simAggregate "orquesta generación/guardado"
                simAppService -> simRepoPort "persiste / recupera"
                simAggregate -> scheduleCalc "genera el cronograma"
                simAggregate -> indicatorsCalc "evalúa indicadores"
                simRepoJpa -> simRepoPort "implementa"

                # Referencias by-id (ACL) — el core no importa el modelo del otro contexto
                simAppService -> clientsComp "valida el cliente (by-id)" {
                    tags "acl"
                }
                simAppService -> offersComp "lee precio de venta / moneda (by-id)" {
                    tags "acl"
                }
            }

            db = container "Database" "Almacena los agregados; FKs reales intra-agregado, referencias by-id sin FK." "PostgreSQL" {
                tags "database"
            }

            # Relaciones contenedor a contenedor
            simRepoJpa -> db "lee/escribe credit_simulations + schedule_row + grace_period"
            clientsComp -> db "lee/escribe clients"
            offersComp -> db "lee/escribe vehicle_offers"
            iamComp -> db "lee/escribe users"
        }

        # Relaciones de alto nivel
        advisor -> autofinance "Configura, genera, guarda y reabre simulaciones"
        advisor -> idp "Se autentica"
        autofinance -> idp "Verifica la sesión (Conformist)" {
            tags "external"
        }
    }

    views {
        systemContext autofinance "SystemContext" "Contexto de sistema: el asesor opera AutoFinance API; la identidad se delega en el Identity Provider." {
            include *
            autolayout lr
        }

        container autofinance "Container" "Contenedores: REST API (Spring Boot) y base de datos (PostgreSQL)." {
            include *
            autolayout lr
        }

        component api "CoreComponent" "Componentes del REST API; foco en el core Credit Simulation y sus 4 capas." {
            include *
            autolayout lr
        }

        styles {
            element "Person" {
                shape Person
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "Container" {
                background #438dd5
                color #ffffff
            }
            element "Component" {
                background #85bbf0
                color #000000
            }
            element "database" {
                shape Cylinder
            }
            element "core" {
                background #0b4884
                color #ffffff
            }
            element "external" {
                background #999999
                color #ffffff
            }
            relationship "acl" {
                style dashed
            }
        }
    }
}
