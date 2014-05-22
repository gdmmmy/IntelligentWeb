///**
// * Singleton class for accessing configuration settings
// * @author Peter Heywood <aca10pth@shef.ac.uk>
// */
//package conn;
//import java.util.Properties;
//import java.io.*;
//import org.apache.log4j.Logger;
//
//public class Config {
//    // Static variables
//    private static final Logger logger = Logger.getLogger(Config.class);
//
//    // Instance variables
//    private static String CONFIG_INI_PATH = "../.config/config.properties";
//    private static Config instance = null;
//    private Properties props = null;
//    private Boolean loaded = false;
//
//    /**
//     * Constructor which loads the configuration file.
//     */
//    protected Config() {
//        // Attemmpt to laod config from default file, assign value to class property
//        loaded = load(CONFIG_INI_PATH);
//    }
//    
//    /**
//     * Singleton instance accessor method.
//     * @return Singleton instance of the Config class.
//     */
//    public static Config getInstance() {
//        if(instance == null) {
//            instance = new Config();
//        }
//        return instance;
//    }
//
//    /**
//     * Load Properties from the specified path to configuration file.
//     * @param  configPath path to the target configuration file
//     * @return Boolean indicating succes. 
//     */
//    private Boolean load(String configPath) {
//        try {
//            Properties properties = new Properties();
//            properties.load(this.getClass().getClassLoader().getResourceAsStream(configPath));
//            props = properties;
//            return true;
//        } catch (IOException e) {
//            // When file is not found, should not let thigns happen / report configuration error.
//            logger.error("Error loading Config", e);
//            return false;
//        }
//    }
//
//    /**
//     * Get a property from the loaded config file
//     * @param  key key identifier for key-value parings in config file.
//     * @return value for specified key.
//     */
//    public String getProperty(String key) {
//        return props.getProperty(key);
//    }
//
//    /**
//     * Check if a config file has successefully been parsed or not.
//     * @return Boolean indicator of success
//     */
//    public Boolean isLoaded() {
//        return loaded;
//    }
//}
