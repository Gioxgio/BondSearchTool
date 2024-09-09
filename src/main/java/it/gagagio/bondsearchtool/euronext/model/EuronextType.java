package it.gagagio.bondsearchtool.euronext.model;

import it.gagagio.bondsearchtool.model.BondType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public enum EuronextType {

    CORPORATE(List.of("Abm Bank And Insurance Bonds", "Abm Convertible Bonds", "Abm Covered Bonds", "Abm Esg Bonds", "Abm Loan Certificates", "Abm Industry And Commercial Bonds", "Abm Perpetual Hybrid 1", "Abm Senior Preferred Bonds", "Abm Subordinated Bonds", "Abs", "Abs Sts",
            "Bonds",
            "Certificates Of Deposit", "Commercial Papers", "Convertible Bonds", "Corporate Bonds", "Covered Bonds",
            "Euro Medium Term Notes",
            "Financial Bonds",
            "Infrastructure Bonds",
            "Medium Term Notes", "Miscellaneous Credit Linked",
            "Ob Bank And Insurance Bonds", "Ob Convertible Bonds", "Ob Covered Bonds", "Ob Covered Bonds Benchmark", "Ob Esg Bonds", "Ob Government Owned Enterprises Bonds", "Ob Industry And Commercial Bonds", "Ob Mortgage Bank Bonds", "Ob Perpetual Hybrid 1", "Ob Senior Preferred Bonds", "Ob Subordinated Bonds", "Other Debt Instruments",
            "Saving Certificates", "Subordinated Securities", "Supranational/Agency")),
    GOVERNMENT(List.of("Abm Municipal Bonds",
            "Belgian Government Bonds", "Belgian Government Bonds (Linear Bonds)", "Belgian Government Bonds (Treasury Bills)", "Bobl", "Bund", "Bot", "Btp", "Btp Inflation", "Btp Italia", "Btp Futura", "Btp Valore",
            "Cct",
            "Dutch Government Bonds", "Dutch Government Bonds (Interest Coupon Certificates)", "Dutch Government Bonds (Principal Certificates)", "Dutch Government Bonds (Treasury Bills)",
            "Emerging Market Govies", "Extra Eu Govies",
            "Oat", "Oat (Interest Coupon Certificates)", "Oat (Principal Certificates)", "Oat Inflation", "Oat Inflation (Interest Coupon Certificates)", "Oat Inflation (Principal Certificates)", "Ob Government Bills", "Ob Government Bonds", "Ob Municipal Bonds", "Other Italian Govies", "Other Eu Govies",
            "Portuguese Government Bonds",
            "Schatze", "Sovereign",
            "T-Bonds", "T-Note", "Territorial Bonds"));

    private final List<String> subtypes;

    public static Optional<EuronextType> from(final String subtype) {

        for (EuronextType value : values()) {

            if (value.subtypes.contains(subtype)) {
                return Optional.of(value);
            }
        }

        log.warn("Missing subtype: {}", subtype);

        return Optional.empty();
    }

    public BondType toBontType() {
        return switch (this) {
            case CORPORATE -> BondType.CORPORATE;
            case GOVERNMENT -> BondType.GOVERNMENT;
        };
    }
}
