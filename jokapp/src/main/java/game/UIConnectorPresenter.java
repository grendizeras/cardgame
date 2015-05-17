package game;

import android.support.annotation.NonNull;


/**
 * This interface is used as a bridge between UIConnector and GameMatch. This is mainly because activity can be destroyed, so the
 * UIConnector object, that was passed to GameMatch, may no longer be valid. With getUIConnector method GameMatch will always have
 * latest UIConnector object.
 */
public interface UIConnectorPresenter {

    Connector.ConnectorCallback  getUIConnector();

}
