package engine;

import keywords.KeywordLibrary;
import utils.ExcelUtils;

import java.lang.reflect.Method;
import java.util.List;

public class KeywordEngine {

    private KeywordLibrary keywordLib;
    private Method[] methods;

    public KeywordEngine() {
        keywordLib = new KeywordLibrary();
        methods = keywordLib.getClass().getMethods();
    }

    public void runTest(String testCaseId) throws Exception {
        String filePath  = "src/test/resources/testdata/TestCases.xlsx";
        String sheetName = "TestSteps";

        List<String[]> steps =
                ExcelUtils.getStepsForTestCase(filePath, sheetName, testCaseId);

        for (String[] step : steps) {
            String stepNo  = step[0];
            String keyword = step[1];
            String object  = step[2];
            String data    = step[3];

            System.out.println("Executing Step: " + stepNo +
                               " | Keyword: " + keyword +
                               " | Object: " + object +
                               " | Data: " + data);

            invokeKeyword(keyword, object, data);
        }
    }

    private void invokeKeyword(String keyword, String object, String data)
            throws Exception {

        boolean found = false;

        for (Method m : methods) {
            if (m.getName().equalsIgnoreCase(keyword)) {
                found = true;
                int paramCount = m.getParameterCount();

                if (paramCount == 0) {
                    m.invoke(keywordLib);
                } else if (paramCount == 1) {
                    // Decide whether this method expects object or data
                    Class<?> paramType = m.getParameterTypes()[0];
                    if (paramType == String.class) {
                        // For our design: NAVIGATE(url) or CLICK(object)
                        // if object is present → treat as object; else data
                        if (object != null && !object.isEmpty()) {
                            m.invoke(keywordLib, object);
                        } else {
                            m.invoke(keywordLib, data);
                        }
                    }
                } else if (paramCount == 2) {
                    m.invoke(keywordLib, object, data);
                }
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("No method found for keyword: " + keyword);
        }
    }
}
