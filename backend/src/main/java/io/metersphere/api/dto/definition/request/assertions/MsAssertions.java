package io.metersphere.api.dto.definition.request.assertions;

import com.alibaba.fastjson.annotation.JSONType;
import io.metersphere.api.dto.definition.request.MsTestElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.assertions.*;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "Assertions")
public class MsAssertions extends MsTestElement {
    private List<MsAssertionRegex> regex;
    private List<MsAssertionJsonPath> jsonPath;
    private List<MsAssertionJSR223> jsr223;
    private List<MsAssertionXPath2> xpath2;
    private MsAssertionDuration duration;
    private String type = "Assertions";

    public void toHashTree(HashTree tree, List<MsTestElement> hashTree) {
        addAssertions(tree);

    }
    private void addAssertions(HashTree hashTree) {
        if (CollectionUtils.isNotEmpty(this.getRegex())) {
            this.getRegex().stream().filter(MsAssertionRegex::isValid).forEach(assertion ->
                    hashTree.add(responseAssertion(assertion))
            );
        }

        if (CollectionUtils.isNotEmpty(this.getJsonPath())) {
            this.getJsonPath().stream().filter(MsAssertionJsonPath::isValid).forEach(assertion ->
                    hashTree.add(jsonPathAssertion(assertion))
            );
        }

        if (CollectionUtils.isNotEmpty(this.getXpath2())) {
            this.getXpath2().stream().filter(MsAssertionXPath2::isValid).forEach(assertion ->
                    hashTree.add(xPath2Assertion(assertion))
            );
        }

        if (CollectionUtils.isNotEmpty(this.getJsr223())) {
            this.getJsr223().stream().filter(MsAssertionJSR223::isValid).forEach(assertion ->
                    hashTree.add(jsr223Assertion(assertion))
            );
        }

        if (this.getDuration().isValid()) {
            hashTree.add(durationAssertion(this.getDuration()));
        }
    }

    private ResponseAssertion responseAssertion(MsAssertionRegex assertionRegex) {
        ResponseAssertion assertion = new ResponseAssertion();
        assertion.setEnabled(true);
        assertion.setName(assertionRegex.getDescription());
        assertion.setProperty(TestElement.TEST_CLASS, ResponseAssertion.class.getName());
        assertion.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("AssertionGui"));
        assertion.setAssumeSuccess(assertionRegex.isAssumeSuccess());
        assertion.setToContainsType();
        switch (assertionRegex.getSubject()) {
            case "Response Code":
                assertion.setTestFieldResponseCode();
                break;
            case "Response Headers":
                assertion.setTestFieldResponseHeaders();
                break;
            case "Response Data":
                assertion.setTestFieldResponseData();
                break;
        }
        return assertion;
    }

    private JSONPathAssertion jsonPathAssertion(MsAssertionJsonPath assertionJsonPath) {
        JSONPathAssertion assertion = new JSONPathAssertion();
        assertion.setEnabled(true);
        assertion.setName(assertionJsonPath.getDescription());
        assertion.setProperty(TestElement.TEST_CLASS, JSONPathAssertion.class.getName());
        assertion.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("JSONPathAssertionGui"));
        assertion.setJsonPath(assertionJsonPath.getExpression());
        assertion.setExpectedValue(assertionJsonPath.getExpect());
        assertion.setJsonValidationBool(true);
        assertion.setExpectNull(false);
        assertion.setInvert(false);
        assertion.setIsRegex(true);
        return assertion;
    }

    private XPath2Assertion xPath2Assertion(MsAssertionXPath2 assertionXPath2) {
        XPath2Assertion assertion = new XPath2Assertion();
        assertion.setEnabled(true);
        assertion.setName(assertionXPath2.getExpression());
        assertion.setProperty(TestElement.TEST_CLASS, XPath2Assertion.class.getName());
        assertion.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("XPath2AssertionGui"));
        assertion.setXPathString(assertionXPath2.getExpression());
        assertion.setNegated(false);
        return assertion;
    }

    private DurationAssertion durationAssertion(MsAssertionDuration assertionDuration) {
        DurationAssertion assertion = new DurationAssertion();
        assertion.setEnabled(true);
        assertion.setName("Response In Time: " + assertionDuration.getValue());
        assertion.setProperty(TestElement.TEST_CLASS, DurationAssertion.class.getName());
        assertion.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("DurationAssertionGui"));
        assertion.setAllowedDuration(assertionDuration.getValue());
        return assertion;
    }

    private JSR223Assertion jsr223Assertion(MsAssertionJSR223 assertionJSR223) {
        JSR223Assertion assertion = new JSR223Assertion();
        assertion.setEnabled(true);
        assertion.setName(assertionJSR223.getDesc());
        assertion.setProperty(TestElement.TEST_CLASS, JSR223Assertion.class.getName());
        assertion.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
        assertion.setProperty("cacheKey", "true");
        assertion.setProperty("scriptLanguage", assertionJSR223.getLanguage());
        assertion.setProperty("script", assertionJSR223.getScript());
        return assertion;
    }

}
