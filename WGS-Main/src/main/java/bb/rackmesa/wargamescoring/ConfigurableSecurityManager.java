package bb.rackmesa.wargamescoring;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;

/**
 * Created by Dan on 11/12/2016.
 */
public class ConfigurableSecurityManager extends DefaultSecurityManager{

    private Configuration configuration = new Configuration();
    private boolean shiroOverride = false;

    public ConfigurableSecurityManager(Realm realm)
    {
        super(realm);
    }

    public ConfigurableSecurityManager()
    {
        super();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setShiroOverride(boolean shiroOverride) {
        this.shiroOverride = shiroOverride;
        SecurityUtils.setSecurityManager(this);
        Configuration.securityManager = this;
    }
}
