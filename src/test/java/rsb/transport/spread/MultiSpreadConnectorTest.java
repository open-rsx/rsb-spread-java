/**
 * ============================================================
 *
 * This file is part of the rsb-java project
 *
 * Copyright (C) 2015 CoR-Lab, Bielefeld University
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

import java.nio.ByteBuffer;

import rsb.QualityOfServiceSpec;
import rsb.QualityOfServiceSpec.Ordering;
import rsb.QualityOfServiceSpec.Reliability;
import rsb.converter.UnambiguousConverterMap;
import rsb.transport.InConnector;
import rsb.transport.OutConnector;
import rsb.testutils.ConnectorCheck;

/**
 * Test for spread connectors which somehow use connection sharing.
 *
 * Isolation and coherence of this test case is quite bad
 *
 * @author jwienke
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class MultiSpreadConnectorTest extends ConnectorCheck {

    @Override
    protected InConnector createInConnector(
            final UnambiguousConverterMap<ByteBuffer> converters)
            throws Throwable {
        final SpreadWrapper inWrapper = Utilities.createSpreadWrapper();
        final InConnector connector =
                new MultiSpreadInConnector(new SpreadMultiReceiver(
                        new SpreadReceiver(inWrapper, converters)));
        return connector;
    }

    @Override
    protected OutConnector createOutConnector(
            final UnambiguousConverterMap<ByteBuffer> converters)
            throws Throwable {
        final SpreadWrapper outWrapper =
                new RefCountingSpreadWrapper(Utilities.createSpreadWrapper());
        final SpreadOutConnector outConnector =
                new SpreadOutConnector(outWrapper, converters,
                        new QualityOfServiceSpec(Ordering.ORDERED,
                                Reliability.RELIABLE));
        return outConnector;
    }

}
