package org.javaee7.cdi.bean.injection;

import javax.inject.Inject;
import org.javaee7.cdi.bean.injection.beans.impl.AfterBeanDiscoveryBean;
import org.javaee7.cdi.bean.injection.beans.impl.AfterTypeDiscoveryBean;
import org.javaee7.cdi.bean.injection.beans.impl.BeforeBeanDiscoveryBean;
import org.javaee7.cdi.bean.injection.beans.impl.RegularBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matt Gill
 */
@RunWith(Arquillian.class)
public class InjectionTest {

    @Deployment
    public static Archive<?> deploy() {
        
        JavaArchive extensionJar = ShrinkWrap.create(JavaArchive.class, "bean-extension.jar")
                .addPackage("org.javaee7.cdi.bean.injection.extension");

        WebArchive war = ShrinkWrap.create(WebArchive.class, "bean-extension-test.war")
                .addPackages(true, "org.javaee7.cdi.bean.injection.beans")
                .addAsResource("WEB-INF/beans.xml")
                .addAsResource("META-INF/persistence.xml")
                .addAsLibrary(extensionJar);
        
        System.out.println(war.toString(true));
        
        return war;
    }

    @Inject
    private BeforeBeanDiscoveryBean beforeBeanDiscoveryBean;

    @Inject
    private AfterTypeDiscoveryBean afterTypeDiscoveryBean;

    @Inject
    private AfterBeanDiscoveryBean afterBeanDiscoveryBean;

    @Inject
    private RegularBean regularBean;

    @Test
    public void before_bean_discovery_bean_not_null() {
        assertTrue(beforeBeanDiscoveryBean.correctlyInjected());
    }

    @Test
    public void after_type_discovery_bean_not_null() {
        assertTrue(afterTypeDiscoveryBean.correctlyInjected());
    }

    @Test
    public void after_bean_discovery_bean_not_null() {
        assertTrue(afterBeanDiscoveryBean.correctlyInjected());
    }

    @Test
    public void regular_bean_not_null() {
        assertTrue(regularBean.correctlyInjected());
    }
}
