package br.com.johanes.api.utils;

import br.com.johanes.api.model.Consumer;
import br.com.johanes.api.model.Subscription;

/**
 *
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Oct 20, 2016
 * @version 1.0
 */
public class ApiHelper {

    public static boolean isJedi(Consumer consumer) {

        return consumer != null && Subscription.JEDI.equals(consumer.getSubscription());
    }
}
