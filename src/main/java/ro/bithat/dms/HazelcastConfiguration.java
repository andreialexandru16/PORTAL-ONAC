//package ro.bithat.dms;
//
//import com.hazelcast.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class HazelcastConfiguration {
//	
//	@Value("${spring.profiles.active}")
//	private String profile;
//	
//    @Bean
//    public Config hazelCastConfig(){
//        Config config = new Config();
//        config.setInstanceName("hazelcast-documentaocrreviewcheckout-" + profile);
////                .addMapConfig(
////                        new MapConfig()
////                                .setName("configuration")
////                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
////                                .setEvictionPolicy(EvictionPolicy.LRU)
////.setTimeToLiveSeconds(-1));
//        return config;
//    }
//}