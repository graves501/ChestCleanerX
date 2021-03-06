package io.github.graves501.chestcleanerx.sorting.evaluator;

import java.util.ArrayList;
import java.util.List;

public enum ItemEvaluatorType {

    BACK_BEGIN_STRING, BEGIN_BACK_STRING;

    /**
     * Returns the enum object if it's is equal to an existing entry.
     *
     * @param evaluatorTypeName the name of the enum entry.
     * @return the enum entry object or {@code null} if does not exist.
     */
    public static ItemEvaluatorType getEvaluatorTypeByName(final String evaluatorTypeName) {
        if (evaluatorTypeName == null) {
            return null;
        }

        if (evaluatorTypeName.equalsIgnoreCase(BACK_BEGIN_STRING.name())) {
            return BACK_BEGIN_STRING;
        } else if (evaluatorTypeName.equalsIgnoreCase(BEGIN_BACK_STRING.name())) {
            return BEGIN_BACK_STRING;
        }
        return null;
    }

    public static List<String> getIDList() {
        List<String> idList = new ArrayList<>();
        idList.add(BACK_BEGIN_STRING.name());
        idList.add(BEGIN_BACK_STRING.name());
        return idList;
    }

    public static Evaluator getEvaluator(final ItemEvaluatorType itemEvaluatorType) {

        if (itemEvaluatorType == null) {
            return null;
        }

        if (itemEvaluatorType.equals(BACK_BEGIN_STRING)) {
            return new BackBeginStringEvaluator();
        } else if (itemEvaluatorType.equals(BEGIN_BACK_STRING)) {
            return new BeginBackStringEvaluator();
        }
        return null;
    }

}
