/* SPDX-License-Identifier: Apache-2.0 */
package org.odpi.openmetadata.adapters.repositoryservices.omrstopicconnector.inmemory;

import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.repositoryservices.events.v1.OMRSEventV1;
import org.odpi.openmetadata.repositoryservices.connectors.omrstopic.OMRSTopicConnector;

import java.util.ArrayList;
import java.util.List;


/**
 * InMemoryOMRSTopicConnector provides a concrete implementation of the OMRSTopicConnector that
 * uses an in-memory list as the event/messaging infrastructure.
 */
public class InMemoryOMRSTopicConnector extends OMRSTopicConnector
{
    private volatile List<OMRSEventV1> inMemoryOMRSTopic = new ArrayList<>();

    public InMemoryOMRSTopicConnector()
    {
        super();
    }

    /**
     * Supports putting events to the in memory OMRS Topic
     *
     * @param newEvent  event to publish
     */
    private synchronized void putEvent(OMRSEventV1  newEvent)
    {
        inMemoryOMRSTopic.add(newEvent);
    }


    /**
     * Returns all of the events found on the in memory OMRS Topic.
     *
     * @return list of received events.
     */
    private synchronized List<OMRSEventV1> getEvents()
    {
        List<OMRSEventV1>   receivedEvents = inMemoryOMRSTopic;
        inMemoryOMRSTopic = new ArrayList<>();

        return receivedEvents;
    }


    /**
     * Sends the supplied event to the topic.
     *
     * @param event  OMRSEvent object containing the event properties.
     */
    public void sendEvent(OMRSEventV1 event)
    {
        this.putEvent(event);

    }


    /**
     * Look to see if there is one of more new events to process.
     *
     * @return a list of received events or null
     */
    protected List<OMRSEventV1> checkForEvents()
    {
        return this.getEvents();
    }


    /**
     * Indicates that the connector is completely configured and can begin processing.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    public void start() throws ConnectorCheckedException
    {
        super.start();
    }


    /**
     * Free up any resources held since the connector is no longer needed.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    public  void disconnect() throws ConnectorCheckedException
    {
        super.disconnect();
    }
}
