package io.metersphere.api.dto.definition.request;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "TestPlan")
public class MsTestPlan extends MsTestElement {
    private String type = "TestPlan";

    public void toHashTree(HashTree tree, List<MsTestElement> hashTree) {
        final HashTree testPlanTree = tree.add(getPlan());
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(testPlanTree, el.getHashTree());
            });
        }
    }

    public TestPlan getPlan() {
        TestPlan testPlan = new TestPlan(this.getName() + "TestPlan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestPlanGui"));
        testPlan.setEnabled(true);
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(true);
        testPlan.setTearDownOnShutdown(true);
        testPlan.setUserDefinedVariables(new Arguments());
        return testPlan;
    }

}
