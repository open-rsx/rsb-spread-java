/**
 * ============================================================
 *
 * This file is part of the rsb-java project
 *
 * Copyright (C) 2015 Jan Moringen
 *
 * This file may be licensed under the terms of the
 * GNU Lesser General Public License Version 3 (the ``LGPL''),
 * or (at your option) any later version.
 *
 * Software distributed under the License is distributed
 * on an ``AS IS'' basis, WITHOUT WARRANTY OF ANY KIND, either
 * express or implied. See the LGPL for the specific language
 * governing rights and limitations.
 *
 * You should have received a copy of the LGPL along with this
 * program. If not, go to http://www.gnu.org/licenses/lgpl.html
 * or write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * The development of this software was supported by:
 *   CoR-Lab, Research Institute for Cognition and Robotics
 *     Bielefeld University
 *
 * ============================================================
 */
package rsb.transport.spread;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for globally available {@link InConnectorFactory} instances.
 *
 * @deprecated this is more or less a hack until the configuration system
 *             supports a better structured way to specify the factory
 *             instances.
 * @author jwienke
 */
@Deprecated
public final class InConnectorFactoryRegistry {

    private static final InConnectorFactoryRegistry INSTANCE =
            new InConnectorFactoryRegistry();

    private final Map<String, InConnectorFactory> factories =
            new HashMap<String, InConnectorFactory>();

    private InConnectorFactoryRegistry() {
        // singleton constructor
    }

    /**
     * Returns the singleton instance.
     *
     * @return singleton instance, not <code>null</code>
     */
    public static InConnectorFactoryRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Register a new {@link InConnectorFactory} instance under the given
     * key.
     *
     * @param key
     *            access key for retrieval via transport options, not
     *            <code>null</code>, not empty
     * @param factory
     *            the factory instance, not <code>null</code>
     */
    public void registerFactory(final String key,
            final InConnectorFactory factory) {
        assert key != null;
        assert factory != null;

        synchronized (this.factories) {
            if (this.factories.containsKey(key)) {
                throw new IllegalArgumentException(
                        "There is already a factory with key '" + key + "':"
                                + this.factories.get(key));
            }
            this.factories.put(key, factory);
        }
    }

    /**
     * Retrieve a factory with a specified key.
     *
     * @param key
     *            the key, not <code>null</code>
     * @return factory or <code>null</code> if nothing is available under the
     *         specified key
     */
    InConnectorFactory get(final String key) {
        assert key != null;
        return this.factories.get(key);
    }

}
