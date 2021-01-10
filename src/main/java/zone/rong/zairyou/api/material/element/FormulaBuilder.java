package zone.rong.zairyou.api.material.element;

import com.google.common.collect.ImmutableMap;

import java.util.function.UnaryOperator;

public class FormulaBuilder {

    private final StringBuilder formulaBuilder = new StringBuilder();

    public static FormulaBuilder of() {
        return new FormulaBuilder();
    }

    FormulaBuilder() { }

    public FormulaBuilder element(Element element) {
        formulaBuilder.append(element.name());
        return this;
    }

    public FormulaBuilder element(Element element, int atoms) {
        formulaBuilder.append(element.name());
        if (atoms != 1) {
            for (char n : Integer.toString(atoms).toCharArray()) {
                formulaBuilder.append((char) ('\u2080' + (n - '0')));
            }
        }
        return this;
    }

    public FormulaBuilder elements(UnaryOperator<ImmutableMap.Builder<Element, Integer>> builder) {
        builder.apply(new ImmutableMap.Builder<>()).build().forEach(this::element);
        return this;
    }

    public FormulaBuilder pos() {
        formulaBuilder.append('\u207A');
        return this;
    }

    public FormulaBuilder neg() {
        formulaBuilder.append('\u207B');
        return this;
    }

    public FormulaBuilder singleBond() {
        formulaBuilder.append('-');
        return this;
    }

    public FormulaBuilder doubleBond() {
        formulaBuilder.append('=');
        return this;
    }

    public FormulaBuilder tripleBond() {
        formulaBuilder.append('≡');
        return this;
    }

    public String build() {
        return formulaBuilder.toString();
    }

}
