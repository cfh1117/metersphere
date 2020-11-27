package io.metersphere.api.dto.definition.request.sampler;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import io.metersphere.api.dto.definition.request.MsTestElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.protocol.tcp.sampler.TCPSampler;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "TCPSampler")
public class MsTCPSampler extends MsTestElement {
    @JSONField(ordinal = 10)
    private String type = "TCPSampler";
    @JSONField(ordinal = 11)
    private String classname = "";
    @JSONField(ordinal = 12)
    private String server = "";
    @JSONField(ordinal = 13)
    private String port = "";
    @JSONField(ordinal = 14)
    private String ctimeout = "";
    @JSONField(ordinal = 15)
    private String timeout = "";
    @JSONField(ordinal = 16)
    private boolean reUseConnection = true;
    @JSONField(ordinal = 17)
    private boolean nodelay;
    @JSONField(ordinal = 18)
    private boolean closeConnection;
    @JSONField(ordinal = 19)
    private String soLinger = "";
    @JSONField(ordinal = 20)
    private String eolByte = "";
    @JSONField(ordinal = 21)
    private String username = "";
    @JSONField(ordinal = 22)
    private String password = "";
    @JSONField(ordinal = 23)
    private String request;

    public void toHashTree(HashTree tree, List<MsTestElement> hashTree) {
        final HashTree samplerHashTree = new ListedHashTree();
        samplerHashTree.add(tcpConfig());
        tree.set(tcpSampler(), samplerHashTree);
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(samplerHashTree, el.getHashTree());
            });
        }
    }

    private TCPSampler tcpSampler() {
        TCPSampler tcpSampler = new TCPSampler();
        tcpSampler.setName(this.getName());
        tcpSampler.setProperty(TestElement.TEST_CLASS, TCPSampler.class.getName());
        tcpSampler.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TCPSamplerGui"));
        tcpSampler.setClassname(this.getClassname());
        tcpSampler.setServer(this.getServer());
        tcpSampler.setPort(this.getPort());
        tcpSampler.setConnectTimeout(this.getCtimeout());
        tcpSampler.setProperty(TCPSampler.RE_USE_CONNECTION, this.isReUseConnection());
        tcpSampler.setProperty(TCPSampler.NODELAY, this.isNodelay());
        tcpSampler.setCloseConnection(String.valueOf(this.isCloseConnection()));
        tcpSampler.setSoLinger(this.getSoLinger());
        tcpSampler.setEolByte(this.getEolByte());
        tcpSampler.setRequestData(this.getRequest());
        tcpSampler.setProperty(ConfigTestElement.USERNAME, this.getUsername());
        tcpSampler.setProperty(ConfigTestElement.PASSWORD, this.getPassword());

        return tcpSampler;
    }

    private ConfigTestElement tcpConfig() {
        ConfigTestElement configTestElement = new ConfigTestElement();
        configTestElement.setEnabled(true);
        configTestElement.setName(this.getName());
        configTestElement.setProperty(TestElement.TEST_CLASS, ConfigTestElement.class.getName());
        configTestElement.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TCPConfigGui"));
        configTestElement.setProperty(TCPSampler.CLASSNAME, this.getClassname());
        configTestElement.setProperty(TCPSampler.SERVER, this.getServer());
        configTestElement.setProperty(TCPSampler.PORT, this.getPort());
        configTestElement.setProperty(TCPSampler.TIMEOUT_CONNECT, this.getCtimeout());
        configTestElement.setProperty(TCPSampler.RE_USE_CONNECTION, this.isReUseConnection());
        configTestElement.setProperty(TCPSampler.NODELAY, this.isNodelay());
        configTestElement.setProperty(TCPSampler.CLOSE_CONNECTION, this.isCloseConnection());
        configTestElement.setProperty(TCPSampler.SO_LINGER, this.getSoLinger());
        configTestElement.setProperty(TCPSampler.EOL_BYTE, this.getEolByte());
        configTestElement.setProperty(TCPSampler.SO_LINGER, this.getSoLinger());
        configTestElement.setProperty(ConfigTestElement.USERNAME, this.getUsername());
        configTestElement.setProperty(ConfigTestElement.PASSWORD, this.getPassword());
        return configTestElement;
    }

}
