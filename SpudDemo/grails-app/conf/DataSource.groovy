dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}

// environment specific settings
environments {
    development {
      dataSource {
        dbCreate = "update"
        url = "jdbc:h2:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        properties {
           maxActive = -1
           minEvictableIdleTimeMillis=1800000
           timeBetweenEvictionRunsMillis=1800000
           numTestsPerEvictionRun=3
           testOnBorrow=true
           testWhileIdle=true
           testOnReturn=false
           validationQuery="SELECT 1"
           jdbcInterceptors="ConnectionState"
        }
      }
      // dataSource {
      //   pooled = true
      //   url = "jdbc:mysql://localhost/spud-demo_development"
      //   driverClassName = "com.mysql.jdbc.Driver"
      //   username = 'root'
      //   password = ''
      //   dbCreate = "update"
      //   dialect = org.hibernate.dialect.MySQL5InnoDBDialect
      //   logSql = true
      //   properties {
      //     validationQuery = 'select 1'
      //     testOnBorrow = true
      //     testOnReturn = false
      //     testWhileIdle = true
      //     timeBetweenEvictionRunsMillis = 300000
      //     numTestsPerEvictionRun = 3
      //     minEvictableIdleTimeMillis = 600000
      //     initialSize = 1
      //     minIdle = 1
      //     maxActive = 10
      //     maxIdle = 10000
      //     maxWait = 90000
      //     removeAbandoned = true
      //     removeAbandonedTimeout = 6000
      //     logAbandoned = true
      //   }
      // }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
            dataSource {
        pooled = true
        url = "jdbc:mysql://localhost/spud-demo_development"
        driverClassName = "com.mysql.jdbc.Driver"
        username = 'root'
        password = ''
        dbCreate = "update"
        dialect = org.hibernate.dialect.MySQL5InnoDBDialect

        properties {
          validationQuery = 'select 1'
          testOnBorrow = true
          testOnReturn = false
          testWhileIdle = true
          timeBetweenEvictionRunsMillis = 300000
          numTestsPerEvictionRun = 3
          minEvictableIdleTimeMillis = 600000
          initialSize = 1
          minIdle = 1
          maxActive = 10
          maxIdle = 10000
          maxWait = 90000
          removeAbandoned = true
          removeAbandonedTimeout = 6000
          logAbandoned = true
        }
      }
        // dataSource {
        //     dbCreate = "update"
        //     url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        //     properties {
        //        maxActive = -1
        //        minEvictableIdleTimeMillis=1800000
        //        timeBetweenEvictionRunsMillis=1800000
        //        numTestsPerEvictionRun=3
        //        testOnBorrow=true
        //        testWhileIdle=true
        //        testOnReturn=false
        //        validationQuery="SELECT 1"
        //        jdbcInterceptors="ConnectionState"
        //     }
        // }
    }
}
