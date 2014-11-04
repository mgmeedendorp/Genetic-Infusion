package seremis.geninfusion.api.magnet.tile;

/**
 * Implementing this class over IMagnetConnector makes heat division heat the connector until heat == getMaxHeat() This
 * will drain networks if getMaxHeat() is too big!
 *
 * @author Seremis
 */
public interface IMagnetConsumer extends IMagnetConnector {

}
