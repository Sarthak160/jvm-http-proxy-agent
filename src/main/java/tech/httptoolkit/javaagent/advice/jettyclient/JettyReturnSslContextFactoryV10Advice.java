package tech.httptoolkit.javaagent.advice.jettyclient;

import net.bytebuddy.asm.Advice;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import tech.httptoolkit.javaagent.HttpProxyAgent;

public class JettyReturnSslContextFactoryV10Advice {
    @Advice.OnMethodExit
    public static void getSslContextFactory(@Advice.Return(readOnly = false) SslContextFactory.Client returnValue) {
        SslContextFactory.Client sslFactory = new SslContextFactory.Client();
        sslFactory.setSslContext(HttpProxyAgent.getInterceptedSslContext());
        try {
            sslFactory.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        returnValue = sslFactory;
    }
}