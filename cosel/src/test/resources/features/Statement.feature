@Statement @regression
Feature: Statement

  Background:
    Given have login with credential login.credential.test1
#    And have system preferences system.preference1
  @MengliHuang @78787878 @netnet @ignore
  Scenario Outline: check statement result
  """
  this script used to create data
  """
    When add OTC agreements <agreement.net.net>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
#    And click back button to previous page
#    And view asset type <asset.type2> agreement asset CASH Page
#    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.net.net              | product.type | trade1 | trade2 | asset.type1         | booking1          | asset.type2             | booking2          | statement.info       |
      | agreement.net.net001.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call     | asset.type.qtp.cash.usd | booking1.delivery | net.net001.bilateral |
      | agreement.net.net002.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call     | asset.type.qtp.cash.usd | booking1.delivery | net.net002.bilateral |
      | agreement.net.net003.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call     | asset.type.qtp.cash.usd | booking1.delivery | net.net003.bilateral |
      | agreement.net.net004.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call     | asset.type.qtp.cash.usd | booking1.delivery | net.net004.bilateral |
      | agreement.net.net015.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.delivery | asset.type.qtp.cash.usd | booking1.delivery | net.net015.bilateral |
      | agreement.net.net016.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.delivery | asset.type.qtp.cash.usd | booking1.delivery | net.net016.bilateral |
      | agreement.net.net017.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.delivery | asset.type.qtp.cash.usd | booking1.delivery | net.net017.bilateral |

  @MengliHuang @78787878 @netnet @ignore
  Scenario Outline: check statement result2
  """
  this script used to create data
  """
    When add OTC agreements <agreement.net.net>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.net.net              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info       |
      | agreement.net.net005.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net005.bilateral |
      | agreement.net.net006.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net006.bilateral |
      | agreement.net.net007.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net007.bilateral |
      | agreement.net.net008.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net008.bilateral |
      | agreement.net.net009.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net009.bilateral |
      | agreement.net.net010.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net010.bilateral |
      | agreement.net.net011.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net011.bilateral |
      | agreement.net.net012.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net012.bilateral |
      | agreement.net.net013.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net013.bilateral |
      | agreement.net.net014.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net014.bilateral |
      | agreement.net.net018.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net018.bilateral |
      | agreement.net.net019.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net019.bilateral |
      | agreement.net.net020.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net020.bilateral |
      | agreement.net.net021.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.net021.bilateral |

  @MengliHuang @78787878 @netgross @ignore
  Scenario Outline: check statement result3
  """
  this script used to create data
  """
    When add OTC agreements <agreement.net.gross>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
#    And click back button to previous page
#    And view asset type <asset.type2> agreement asset CASH Page
#    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.net.gross              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info         |
      | agreement.net.gross001.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross001.bilateral |
      | agreement.net.gross002.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross002.bilateral |
      | agreement.net.gross007.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross007.bilateral |
      | agreement.net.gross008.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross008.bilateral |
      | agreement.net.gross009.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross009.bilateral |
      | agreement.net.gross010.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross010.bilateral |
      | agreement.net.gross011.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross011.bilateral |

  @MengliHuang @78787878 @netgross @ignore
  Scenario Outline: check statement result4
  """
  this script used to create data
  """
    When add OTC agreements <agreement.net.gross>
    And view statement
#    And edit summary exposure info
#    And view product type <product.type> on exposure summary page
#    And add OTC trades <trade1>,<trade2>
#    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.net.gross              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info         |
      | agreement.net.gross005.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross005.bilateral |
      | agreement.net.gross006.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross006.bilateral |

  @MengliHuang @78787878 @netgross @ignore
  Scenario Outline: check statement result5
  """
  this script used to create data
  """
    When add OTC agreements <agreement.net.gross>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.net.gross              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info         |
      | agreement.net.gross003.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross003.bilateral |
      | agreement.net.gross004.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross004.bilateral |
      | agreement.net.gross012.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross012.bilateral |
      | agreement.net.gross013.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross013.bilateral |
      | agreement.net.gross014.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross014.bilateral |
      | agreement.net.gross015.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross015.bilateral |
      | agreement.net.gross016.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | net.gross016.bilateral |

  @MengliHuang @78787878 @nonnet-net @ignore
  Scenario Outline: check statement result6
  """
  this script used to create data
  """
    When add OTC agreements <agreement.nonnet.net>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
#    And click back button to previous page
#    And view asset type <asset.type2> agreement asset CASH Page
#    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.nonnet.net              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info          |
      | agreement.nonnet.net001.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net001.bilateral |
      | agreement.nonnet.net002.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net002.bilateral |
      | agreement.nonnet.net010.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net010.bilateral |
      | agreement.nonnet.net011.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net011.bilateral |

  @MengliHuang @78787878 @nonnet-net @ignore
  Scenario Outline: check statement result7
  """
  this script used to create data
  """
    When add OTC agreements <agreement.nonnet.net>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.nonnet.net              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info          |
      | agreement.nonnet.net003.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net003.bilateral |
      | agreement.nonnet.net004.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net004.bilateral |
      | agreement.nonnet.net005.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net005.bilateral |
      | agreement.nonnet.net006.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net006.bilateral |
      | agreement.nonnet.net007.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net007.bilateral |
      | agreement.nonnet.net008.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net008.bilateral |
      | agreement.nonnet.net009.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net009.bilateral |
      | agreement.nonnet.net012.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net012.bilateral |
      | agreement.nonnet.net013.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net013.bilateral |
      | agreement.nonnet.net014.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net014.bilateral |
      | agreement.nonnet.net015.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net015.bilateral |
      | agreement.nonnet.net016.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net016.bilateral |
      | agreement.nonnet.net017.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net017.bilateral |
      | agreement.nonnet.net018.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net018.bilateral |
      | agreement.nonnet.net019.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net019.bilateral |
      | agreement.nonnet.net020.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net020.bilateral |
      | agreement.nonnet.net021.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.net021.bilateral |

  @MengliHuang @78787878 @nonnet-gross @ignore
  Scenario Outline: check statement result8
  """
  this script used to create data
  """
    When add OTC agreements <agreement.nonnet.gross>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.nonnet.gross              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info            |
      | agreement.nonnet.gross001.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross001.bilateral |
      | agreement.nonnet.gross002.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross002.bilateral |
      | agreement.nonnet.gross003.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross003.bilateral |
      | agreement.nonnet.gross004.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross004.bilateral |
      | agreement.nonnet.gross005.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross005.bilateral |
      | agreement.nonnet.gross006.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross006.bilateral |
      | agreement.nonnet.gross007.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross007.bilateral |
      | agreement.nonnet.gross008.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross008.bilateral |
      | agreement.nonnet.gross009.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross009.bilateral |
      | agreement.nonnet.gross010.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross010.bilateral |
      | agreement.nonnet.gross011.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross011.bilateral |
      | agreement.nonnet.gross012.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross012.bilateral |
      | agreement.nonnet.gross013.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross013.bilateral |
      | agreement.nonnet.gross014.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross014.bilateral |
      | agreement.nonnet.gross015.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross015.bilateral |
      | agreement.nonnet.gross016.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | nonnet.gross016.bilateral |

  @MengliHuang @78787878 @autonet-net @ignore
  Scenario Outline: check statement result9
  """
  this script used to create data
  """
    When add OTC agreements <agreement.autonet.net>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.autonet.net              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info           |
      | agreement.autonet.net001.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net001.bilateral |
      | agreement.autonet.net002.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net002.bilateral |
      | agreement.autonet.net003.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net003.bilateral |
      | agreement.autonet.net004.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net004.bilateral |
      | agreement.autonet.net007.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net007.bilateral |
      | agreement.autonet.net008.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net008.bilateral |
      | agreement.autonet.net009.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net009.bilateral |
      | agreement.autonet.net010.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net010.bilateral |
      | agreement.autonet.net011.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net011.bilateral |
      | agreement.autonet.net012.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net012.bilateral |
      | agreement.autonet.net013.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net013.bilateral |
      | agreement.autonet.net014.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net014.bilateral |
      | agreement.autonet.net015.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net015.bilateral |
      | agreement.autonet.net016.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net016.bilateral |
      | agreement.autonet.net017.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net017.bilateral |
      | agreement.autonet.net018.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net018.bilateral |
      | agreement.autonet.net019.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net019.bilateral |
      | agreement.autonet.net020.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net020.bilateral |
      | agreement.autonet.net021.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net021.bilateral |
      | agreement.autonet.net022.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net022.bilateral |
      | agreement.autonet.net023.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net023.bilateral |
      | agreement.autonet.net024.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net024.bilateral |
      | agreement.autonet.net025.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net025.bilateral |
      | agreement.autonet.net026.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net026.bilateral |
      | agreement.autonet.net027.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net027.bilateral |
      | agreement.autonet.net028.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net028.bilateral |
      | agreement.autonet.net029.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net029.bilateral |
      | agreement.autonet.net030.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net030.bilateral |

  @MengliHuang @78787878 @autonet-net @ignore
  Scenario Outline: check statement result10
  """
  this script used to create data
  """
    When add OTC agreements <agreement.autonet.net>
    And view statement
#    And edit summary exposure info
#    And view product type <product.type> on exposure summary page
#    And add OTC trades <trade1>,<trade2>
#    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.autonet.net              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info           |
      | agreement.autonet.net005.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net005.bilateral |
      | agreement.autonet.net006.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.net006.bilateral |

  @MengliHuang @78787878 @autonet-gross @ignore
  Scenario Outline: check statement result11
  """
  this script used to create data
  """
    When add OTC agreements <agreement.autonet.gross>
    And view statement
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade1>,<trade2>
    And quick link - view statement
    And edit asset summary info
    And view asset type <asset.type1> agreement asset CASH Page
    And add call bookings - statement booking <booking1>
    And click back button to previous page
    And view asset type <asset.type2> agreement asset CASH Page
    And add call bookings - statement booking <booking2>
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.autonet.gross              | product.type | trade1 | trade2 | asset.type1         | booking1      | asset.type2             | booking2          | statement.info             |
      | agreement.autonet.gross001.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross001.bilateral |
      | agreement.autonet.gross002.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross002.bilateral |
      | agreement.autonet.gross003.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross003.bilateral |
      | agreement.autonet.gross004.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross004.bilateral |
      | agreement.autonet.gross005.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross005.bilateral |
      | agreement.autonet.gross006.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross006.bilateral |
      | agreement.autonet.gross007.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross007.bilateral |
      | agreement.autonet.gross008.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross008.bilateral |
      | agreement.autonet.gross009.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross009.bilateral |
      | agreement.autonet.gross010.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross010.bilateral |
      | agreement.autonet.gross011.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross011.bilateral |
      | agreement.autonet.gross012.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross012.bilateral |
      | agreement.autonet.gross013.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross013.bilateral |
      | agreement.autonet.gross014.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross014.bilateral |
      | agreement.autonet.gross015.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross015.bilateral |
      | agreement.autonet.gross016.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross016.bilateral |
      | agreement.autonet.gross017.bilateral | product.qtp  | trade1 | trade2 | asset.type.CASH.USD | booking1.call | asset.type.qtp.cash.usd | booking1.delivery | autonet.gross017.bilateral |

  @MengliHuang @1234567
  Scenario Outline: verify statement margin requirement result
    When search agreement <agreement.search.filter>
    And view completed agreement statement <agreement.search.result>
    And recalculate agreement statement
    Then agreement statement should be <statement.info>
    Examples:
      | agreement.search.filter | agreement.search.result    | statement.info             |
      | filter.net.net001       | agreement.net.net001       | statement.net.net001       |
      | filter.net.net002       | agreement.net.net002       | statement.net.net002       |
      | filter.net.net003       | agreement.net.net003       | statement.net.net003       |
      | filter.net.net004       | agreement.net.net004       | statement.net.net004       |
      | filter.net.net005       | agreement.net.net005       | statement.net.net005       |
      | filter.net.net006       | agreement.net.net006       | statement.net.net006       |
      | filter.net.net007       | agreement.net.net007       | statement.net.net007       |
      | filter.net.net008       | agreement.net.net008       | statement.net.net008       |
      | filter.net.net009       | agreement.net.net009       | statement.net.net009       |
      | filter.net.net010       | agreement.net.net010       | statement.net.net010       |
      | filter.net.net011       | agreement.net.net011       | statement.net.net011       |
      | filter.net.net012       | agreement.net.net012       | statement.net.net012       |
      | filter.net.net013       | agreement.net.net013       | statement.net.net013       |
      | filter.net.net014       | agreement.net.net014       | statement.net.net014       |
      | filter.net.net015       | agreement.net.net015       | statement.net.net015       |
      | filter.net.net016       | agreement.net.net016       | statement.net.net016       |
      | filter.net.net017       | agreement.net.net017       | statement.net.net017       |
      | filter.net.net018       | agreement.net.net018       | statement.net.net018       |
      | filter.net.net019       | agreement.net.net019       | statement.net.net019       |
      | filter.net.net020       | agreement.net.net020       | statement.net.net020       |
      | filter.net.net021       | agreement.net.net021       | statement.net.net021       |
      | filter.net.gross001     | agreement.net.gross001     | statement.net.gross001     |
      | filter.net.gross002     | agreement.net.gross002     | statement.net.gross002     |
      | filter.net.gross003     | agreement.net.gross003     | statement.net.gross003     |
      | filter.net.gross004     | agreement.net.gross004     | statement.net.gross004     |
      | filter.net.gross005     | agreement.net.gross005     | statement.net.gross005     |
      | filter.net.gross006     | agreement.net.gross006     | statement.net.gross006     |
      | filter.net.gross007     | agreement.net.gross007     | statement.net.gross007     |
      | filter.net.gross008     | agreement.net.gross008     | statement.net.gross008     |
      | filter.net.gross009     | agreement.net.gross009     | statement.net.gross009     |
      | filter.net.gross010     | agreement.net.gross010     | statement.net.gross010     |
      | filter.net.gross011     | agreement.net.gross011     | statement.net.gross011     |
      | filter.net.gross012     | agreement.net.gross012     | statement.net.gross012     |
      | filter.net.gross013     | agreement.net.gross013     | statement.net.gross013     |
      | filter.net.gross014     | agreement.net.gross014     | statement.net.gross014     |
      | filter.net.gross015     | agreement.net.gross015     | statement.net.gross015     |
      | filter.net.gross016     | agreement.net.gross016     | statement.net.gross016     |
      | filter.nonnet.net001    | agreement.nonnet.net001    | statement.nonnet.net001    |
      | filter.nonnet.net002    | agreement.nonnet.net002    | statement.nonnet.net002    |
      | filter.nonnet.net003    | agreement.nonnet.net003    | statement.nonnet.net003    |
      | filter.nonnet.net004    | agreement.nonnet.net004    | statement.nonnet.net004    |
      | filter.nonnet.net005    | agreement.nonnet.net005    | statement.nonnet.net005    |
      | filter.nonnet.net006    | agreement.nonnet.net006    | statement.nonnet.net006    |
      | filter.nonnet.net007    | agreement.nonnet.net007    | statement.nonnet.net007    |
      | filter.nonnet.net008    | agreement.nonnet.net008    | statement.nonnet.net008    |
      | filter.nonnet.net009    | agreement.nonnet.net009    | statement.nonnet.net009    |
      | filter.nonnet.net010    | agreement.nonnet.net010    | statement.nonnet.net010    |
      | filter.nonnet.net011    | agreement.nonnet.net011    | statement.nonnet.net011    |
      | filter.nonnet.net012    | agreement.nonnet.net012    | statement.nonnet.net012    |
      | filter.nonnet.net013    | agreement.nonnet.net013    | statement.nonnet.net013    |
      | filter.nonnet.net014    | agreement.nonnet.net014    | statement.nonnet.net014    |
      | filter.nonnet.net015    | agreement.nonnet.net015    | statement.nonnet.net015    |
      | filter.nonnet.net016    | agreement.nonnet.net016    | statement.nonnet.net016    |
      | filter.nonnet.net017    | agreement.nonnet.net017    | statement.nonnet.net017    |
      | filter.nonnet.net018    | agreement.nonnet.net018    | statement.nonnet.net018    |
      | filter.nonnet.net019    | agreement.nonnet.net019    | statement.nonnet.net019    |
      | filter.nonnet.net020    | agreement.nonnet.net020    | statement.nonnet.net020    |
      | filter.nonnet.net021    | agreement.nonnet.net021    | statement.nonnet.net021    |
      | filter.nonnet.gross001  | agreement.nonnet.gross001  | statement.nonnet.gross001  |
      | filter.nonnet.gross002  | agreement.nonnet.gross002  | statement.nonnet.gross002  |
      | filter.nonnet.gross003  | agreement.nonnet.gross003  | statement.nonnet.gross003  |
      | filter.nonnet.gross004  | agreement.nonnet.gross004  | statement.nonnet.gross004  |
      | filter.nonnet.gross005  | agreement.nonnet.gross005  | statement.nonnet.gross005  |
      | filter.nonnet.gross006  | agreement.nonnet.gross006  | statement.nonnet.gross006  |
      | filter.nonnet.gross007  | agreement.nonnet.gross007  | statement.nonnet.gross007  |
      | filter.nonnet.gross008  | agreement.nonnet.gross008  | statement.nonnet.gross008  |
      | filter.nonnet.gross009  | agreement.nonnet.gross009  | statement.nonnet.gross009  |
      | filter.nonnet.gross010  | agreement.nonnet.gross010  | statement.nonnet.gross010  |
      | filter.nonnet.gross011  | agreement.nonnet.gross011  | statement.nonnet.gross011  |
      | filter.nonnet.gross012  | agreement.nonnet.gross012  | statement.nonnet.gross012  |
      | filter.nonnet.gross013  | agreement.nonnet.gross013  | statement.nonnet.gross013  |
      | filter.nonnet.gross014  | agreement.nonnet.gross014  | statement.nonnet.gross014  |
      | filter.nonnet.gross015  | agreement.nonnet.gross015  | statement.nonnet.gross015  |
      | filter.nonnet.gross016  | agreement.nonnet.gross016  | statement.nonnet.gross016  |
      | filter.autonet.net001   | agreement.autonet.net001   | statement.autonet.net001   |
      | filter.autonet.net002   | agreement.autonet.net002   | statement.autonet.net002   |
      | filter.autonet.net003   | agreement.autonet.net003   | statement.autonet.net003   |
      | filter.autonet.net004   | agreement.autonet.net004   | statement.autonet.net004   |
      | filter.autonet.net005   | agreement.autonet.net005   | statement.autonet.net005   |
      | filter.autonet.net006   | agreement.autonet.net006   | statement.autonet.net006   |
      | filter.autonet.net007   | agreement.autonet.net007   | statement.autonet.net007   |
      | filter.autonet.net008   | agreement.autonet.net008   | statement.autonet.net008   |
      | filter.autonet.net009   | agreement.autonet.net009   | statement.autonet.net009   |
      | filter.autonet.net010   | agreement.autonet.net010   | statement.autonet.net010   |
      | filter.autonet.net011   | agreement.autonet.net011   | statement.autonet.net011   |
      | filter.autonet.net012   | agreement.autonet.net012   | statement.autonet.net012   |
      | filter.autonet.net013   | agreement.autonet.net013   | statement.autonet.net013   |
      | filter.autonet.net014   | agreement.autonet.net014   | statement.autonet.net014   |
      | filter.autonet.net015   | agreement.autonet.net015   | statement.autonet.net015   |
      | filter.autonet.net016   | agreement.autonet.net016   | statement.autonet.net016   |
      | filter.autonet.net017   | agreement.autonet.net017   | statement.autonet.net017   |
      | filter.autonet.net018   | agreement.autonet.net018   | statement.autonet.net018   |
      | filter.autonet.net019   | agreement.autonet.net019   | statement.autonet.net019   |
      | filter.autonet.net020   | agreement.autonet.net020   | statement.autonet.net020   |
      | filter.autonet.net021   | agreement.autonet.net021   | statement.autonet.net021   |
      | filter.autonet.net022   | agreement.autonet.net022   | statement.autonet.net022   |
      | filter.autonet.net023   | agreement.autonet.net023   | statement.autonet.net023   |
      | filter.autonet.net024   | agreement.autonet.net024   | statement.autonet.net024   |
      | filter.autonet.net025   | agreement.autonet.net025   | statement.autonet.net025   |
      | filter.autonet.net026   | agreement.autonet.net026   | statement.autonet.net026   |
      | filter.autonet.net027   | agreement.autonet.net027   | statement.autonet.net027   |
      | filter.autonet.net028   | agreement.autonet.net028   | statement.autonet.net028   |
      | filter.autonet.net029   | agreement.autonet.net029   | statement.autonet.net029   |
      | filter.autonet.net030   | agreement.autonet.net030   | statement.autonet.net030   |
#      | filter.autonet.gross001 | agreement.autonet.gross001 | statement.autonet.gross001 |
#      | filter.autonet.gross002 | agreement.autonet.gross002 | statement.autonet.gross002 |
#      | filter.autonet.gross003 | agreement.autonet.gross003 | statement.autonet.gross003 |
#      | filter.autonet.gross004 | agreement.autonet.gross004 | statement.autonet.gross004 |
#      | filter.autonet.gross005 | agreement.autonet.gross005 | statement.autonet.gross005 |
#      | filter.autonet.gross006 | agreement.autonet.gross006 | statement.autonet.gross006 |
#      | filter.autonet.gross007 | agreement.autonet.gross007 | statement.autonet.gross007 |
#      | filter.autonet.gross008 | agreement.autonet.gross008 | statement.autonet.gross008 |
#      | filter.autonet.gross009 | agreement.autonet.gross009 | statement.autonet.gross009 |
#      | filter.autonet.gross010 | agreement.autonet.gross010 | statement.autonet.gross010 |
#      | filter.autonet.gross011 | agreement.autonet.gross011 | statement.autonet.gross011 |
#      | filter.autonet.gross012 | agreement.autonet.gross012 | statement.autonet.gross012 |
#      | filter.autonet.gross013 | agreement.autonet.gross013 | statement.autonet.gross013 |
#      | filter.autonet.gross014 | agreement.autonet.gross014 | statement.autonet.gross014 |
#      | filter.autonet.gross015 | agreement.autonet.gross015 | statement.autonet.gross015 |
#      | filter.autonet.gross016 | agreement.autonet.gross016 | statement.autonet.gross016 |
#      | filter.autonet.gross017 | agreement.autonet.gross017 | statement.autonet.gross017 |

  @MengliHuang @Statement @T32743 @Reviewd
  Scenario Outline: Verify repo margin calculation-Exempt is Y and Net Margin requirement is less than MTA and greater than Buffer-Delivery original
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    And edit summary exposure info
    And view product type reverse.repo on exposure summary page
    And add Repo trade <trade1>
    And view trade <trade1> detail
    Then trade detail should be <trade1.detail>
    When trade detail - click cancel button
    And click back button to previous page
    And view product type buy.sell on exposure summary page
    And add Repo trade <trade2>
    And view trade <trade2> detail
    Then trade detail should be <trade2.detail>
    When trade detail - click cancel button
    And click back button to previous page
    And view product type repo on exposure summary page
    And add Repo trade <trade3>
    And view trade <trade3> detail
    Then trade detail should be <trade3.detail>
    When trade detail - click cancel button
    And click back button to previous page
    And view product type sell.buy on exposure summary page
    And add Repo trade <trade4>
    And view trade <trade4> detail
    Then trade detail should be <trade4.detail>
    When trade detail - click cancel button
    And quick link - view statement
    Then agreement statement should be <statement.info>
    Examples:
      | trade1                | trade1.detail                                 | trade2            | trade2.detail                                | trade3        | trade3.detail                                | trade4            | trade4.detail                               | statement.info                          |
      | reverse.repo.trade1.n | trade1.detail.finra4210.haircut.buffer.-0.09m | buy.sell.trade2.n | trade2.detail.finra4210.haircut.buffer.-0.9m | repo.trade3.y | trade3.detail.finra4210.haircut.buffer.0.09m | sell.buy.trade4.y | trade4.detail.finra4210.haircut.buffer.0.9m | statement.p.exempt.y.c.exempt.n.buffer0 |
      | reverse.repo.trade1.y | trade1.detail.finra4210.haircut.buffer.-0.9m  | buy.sell.trade2.y | trade2.detail.finra4210.haircut.buffer.-0.9m | repo.trade3.y | trade3.detail.finra4210.haircut.buffer.2.7m  | sell.buy.trade4.y | trade4.detail.finra4210.haircut.buffer.3.6m | statement.p.exempt.n                    |
      | reverse.repo.trade3.y | trade1.detail.finra4210.haircut.buffer.0.9m   | buy.sell.trade4.n | trade2.detail.finra4210.haircut.buffer.0.9m  | repo.trade1.y | trade3.detail.finra4210.haircut.buffer.0.9m  | sell.buy.trade2.y | trade4.detail.finra4210.haircut.buffer.0.9m | statement.p.exempt.n                    |

  @MengliHuang @Statement @T8882 @Reviewd
  Scenario: Verify FCM Statement will calculate TSA rule included elements - Trade Added
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    And edit summary exposure info
    And view product type product.QTP on exposure summary page
    And add FCM trades trade
    And quick link - view statement
    Then agreement statement should be statement.info

  @MengliHuang @Statement @T11623 @Reviewd
  Scenario Outline: Verify Calc FCM Statement
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    And edit asset summary info
    And view asset type qtp.bond.usd agreement asset Bond Page
    And add call bookings - statement booking <booking1>,<booking2>
    And click back button to previous page
    And view asset type cash.usd agreement asset CASH Page
    And add call bookings - statement booking <booking3>,<booking4>
    And quick link - view statement
    And edit summary exposure info
    And view product type product.QTP on exposure summary page
    And add FCM trades trade1,trade2
    And quick link - view statement
    Then agreement statement should be statement.info
    Examples:
      | booking1            | booking2        | booking3            | booking4        |
      | bond.call.im.3k     | bond.call.vm.2k | cash.delivery.im.1k | cash.call.vm.2k |
      | bond.call.im.1k     | bond.call.vm.2k | cash.call.im.7k     | cash.call.vm.2k |
      | bond.call.im.1k     | bond.call.vm.2k | cash.call.im.2k     | cash.call.vm.2k |
      | bond.delivery.im.1k | bond.call.vm.2k | cash.delivery.im.1k | cash.call.vm.2k |
      | bond.call.im.2k     | bond.call.vm.2k | cash.call.im.1k     | cash.call.vm.2k |

  @MengliHuang @T24075 @todo
  Scenario: Verify statement calc correctly for FCM Multi-Model agreement when TSA level is Component
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    And select model model1
    Then agreement statement should be model1.agreement.statement.info
    When quick link - view statement
    And select model model2
    Then agreement statement should be model2.agreement.statement.info
    When quick link - view statement
    Then multi model agreement statement summary should be multimodel.agreement.statement.summary
    When quick link - agreement exposure management
    And Exposure Management - add all columns
    And Exposure Management - expand events vm.top.Event
    Then Exposure Management - event should be vm.top.Event,vm.model1.event,vm.model2.event

  @MengliHuang @T32692 @todo
  Scenario: Verify haircut adjustment calc when Agreement have default haircut with adjust rule, template have default haircut with adjust rule
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    Then agreement statement should be agreement.statement.info
    When edit asset summary info
    And view asset type booking1 agreement asset Bond Page
    Then Security asset booking summary should be booking1,booking2
    When quick link - show exposure statement
    Then exposure statement should be exposure.statement

  @MengliHuang @T32652 @todo
  Scenario: Verify haircut adjust rule Override Instrument ID equals
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    Then agreement statement should be agreement.statement.info
    When edit asset summary info
    Then asset holding summary should be asset.holding.summary
    When view asset type security.summary.ins1 agreement asset Bond Page
    Then Security asset booking summary should be security.summary.ins1,security.summary.ins2,security.summary.ins3,security.summary.booking1,security.summary.booking2,security.summary.booking3,security.summary.booking4,security.summary.booking5

  @MengliHuang @T32665 @todo
  Scenario: Verify haircut adjust rule Adjust Currency equals
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    Then agreement statement should be agreement.statement.info
    When edit asset summary info
    And view asset type security.summary.ins1 agreement asset Bond Page
    Then Security asset booking summary should be security.summary.ins1,security.summary.ins2
    When quick link - view statement
    And Agreement Admin - manual send Margin Return letter margin.letter.return to letter summary page
    Then letter summary should be margin.return.letter.summary.booking
    When Letter Summary - click Agreement Statement
    And Agreement Admin - manual send Margin Recall letter margin.letter.recall to letter summary page
    Then letter summary should be margin.recall.letter.summary.booking

  @Jane @F13884 @34554 @15.2.0
  Scenario Outline: Verify the Gross Calc calculation correctly with Gross Calc setting Prc Gross for Net agreement
    When Go to agreement <agr34554.net> statement page by URL
    And quick link - agreement review
    And edit OTC agreement <agr34554.net.net> to <agr34554.net.prcGross>
    And view statement
    And recalculate agreement statement
    Then agreement statement should be <statement.info>


    Examples:
      | agr34554.net     | agr34554.net.net     | agr34554.net.prcGross     | statement.info             |
      | agr34554.net.001 | agr34554.net.net.001 | agr34554.net.prcGross.001 | statement.net.prcGross.001 |
      | agr34554.net.002 | agr34554.net.net.002 | agr34554.net.prcGross.002 | statement.net.prcGross.002 |
      | agr34554.net.003 | agr34554.net.net.003 | agr34554.net.prcGross.003 | statement.net.prcGross.003 |
      | agr34554.net.004 | agr34554.net.net.004 | agr34554.net.prcGross.004 | statement.net.prcGross.004 |
      | agr34554.net.005 | agr34554.net.net.005 | agr34554.net.prcGross.005 | statement.net.prcGross.005 |
      | agr34554.net.006 | agr34554.net.net.006 | agr34554.net.prcGross.006 | statement.net.prcGross.006 |
      | agr34554.net.007 | agr34554.net.net.007 | agr34554.net.prcGross.007 | statement.net.prcGross.007 |
      | agr34554.net.008 | agr34554.net.net.008 | agr34554.net.prcGross.008 | statement.net.prcGross.008 |
      | agr34554.net.009 | agr34554.net.net.009 | agr34554.net.prcGross.009 | statement.net.prcGross.009 |


  @Jane @F13884 @34765 @15.2.0
  Scenario Outline: Verify the Gross Calc calculation correctly with Gross Calc setting Cpty Gross for Net agreement
    When Go to agreement <agr34765.net> statement page by URL
    And quick link - agreement review
    And edit OTC agreement <agr34765.net.net> to <agr34765.net.cptyGross>
    And view statement
    And recalculate agreement statement
    Then agreement statement should be <statement.info>
    Examples:
      | agr34765.net     | agr34765.net.net     | agr34765.net.cptyGross     | statement.info             |
      | agr34765.net.001 | agr34765.net.net.001 | agr34765.net.cptyGross.001 | statement.net.cptyGross.001 |
      | agr34765.net.002 | agr34765.net.net.002 | agr34765.net.cptyGross.002 | statement.net.cptyGross.002 |
      | agr34765.net.003 | agr34765.net.net.003 | agr34765.net.cptyGross.003 | statement.net.cptyGross.003 |
      | agr34765.net.004 | agr34765.net.net.004 | agr34765.net.cptyGross.004 | statement.net.cptyGross.004 |
      | agr34765.net.005 | agr34765.net.net.005 | agr34765.net.cptyGross.005 | statement.net.cptyGross.005 |
      | agr34765.net.006 | agr34765.net.net.006 | agr34765.net.cptyGross.006 | statement.net.cptyGross.006 |
      | agr34765.net.007 | agr34765.net.net.007 | agr34765.net.cptyGross.007 | statement.net.cptyGross.007 |


  @Jane @F13884 @34766 @15.2.0
  Scenario Outline: Verify the Gross Calc calculation correctly with Gross Calc setting Prc Gross for Non Net agreement
    When Go to agreement <agr34766.notnet> statement page by URL
    And quick link - agreement review
    And edit OTC agreement <agr34766.notnet.net> to <agr34766.notnet.prcGross> in document tab
    And view statement
    And recalculate agreement statement
    Then agreement statement should be <statement.info>

    Examples:
      | agr34766.notnet     | agr34766.notnet.net     | agr34766.notnet.prcGross     | statement.info             |
      | agr34766.notnet.001 | agr34766.notnet.net.001 | agr34766.notnet.prcGross.001 | statement.notnet.prcGross.001 |
      | agr34766.notnet.002 | agr34766.notnet.net.002 | agr34766.notnet.prcGross.002 | statement.notnet.prcGross.002 |
      | agr34766.notnet.003 | agr34766.notnet.net.003 | agr34766.notnet.prcGross.003 | statement.notnet.prcGross.003 |
      | agr34766.notnet.004 | agr34766.notnet.net.004 | agr34766.notnet.prcGross.004 | statement.notnet.prcGross.004 |
      | agr34766.notnet.005 | agr34766.notnet.net.005 | agr34766.notnet.prcGross.005 | statement.notnet.prcGross.005 |
      | agr34766.notnet.006 | agr34766.notnet.net.006 | agr34766.notnet.prcGross.006 | statement.notnet.prcGross.006 |
      | agr34766.notnet.007 | agr34766.notnet.net.007 | agr34766.notnet.prcGross.007 | statement.notnet.prcGross.007 |
      | agr34766.notnet.008 | agr34766.notnet.net.008 | agr34766.notnet.prcGross.008 | statement.notnet.prcGross.008 |

  @Jane @F13884 @34568 @15.2.0
  Scenario Outline: Verify the Gross Calc calculation correctly with Gross Calc setting Cpty Gross for NonNet agreement
    When Go to agreement <agr34568.notnet> statement page by URL
    And quick link - agreement review
    And edit OTC agreement <agr34568.notnet.net> to <agr34568.notnet.vmCptyGross.imGross> in document tab
    And view statement
    And recalculate agreement statement
    Then agreement statement should be <statement.info>

    Examples:
      | agr34568.notnet     | agr34568.notnet.net     | agr34568.notnet.vmCptyGross.imGross     | statement.info             |
      | agr34568.notnet.001 | agr34568.notnet.net.001 | agr34568.notnet.cptyGross.001 | statement.notnet.cptyGross.001 |
      | agr34568.notnet.002 | agr34568.notnet.net.002 | agr34568.notnet.cptyGross.002 | statement.notnet.cptyGross.002 |
      | agr34568.notnet.003 | agr34568.notnet.net.003 | agr34568.notnet.cptyGross.003 | statement.notnet.cptyGross.003 |
      | agr34568.notnet.004 | agr34568.notnet.net.004 | agr34568.notnet.cptyGross.004 | statement.notnet.cptyGross.004 |
      | agr34568.notnet.005 | agr34568.notnet.net.005 | agr34568.notnet.cptyGross.005 | statement.notnet.cptyGross.005 |
      | agr34568.notnet.006 | agr34568.notnet.net.006 | agr34568.notnet.cptyGross.006 | statement.notnet.cptyGross.006 |
      | agr34568.notnet.007 | agr34568.notnet.net.007 | agr34568.notnet.cptyGross.007 | statement.notnet.cptyGross.007 |



  @Jane @F13884 @34767 @15.2.0
  Scenario Outline: Verify the VM Gross Calc calculation correctly with Gross Calc setting for Non Net agreement
    When Go to agreement <agr34767.notnet> statement page by URL
    And quick link - agreement review
    And edit OTC agreement <agr34767.notnet.net> to <agr34767.notnet.Gross> in document tab
    And view statement
    And recalculate agreement statement
    Then agreement statement should be <statement.info>

    Examples:
      | agr34767.notnet     | agr34767.notnet.net     | agr34767.notnet.Gross     | statement.info             |
      | agr34767.notnet.001 | agr34767.notnet.net.001 | agr34767.notnet.Gross.001 | statement.notnet.Gross.001 |
      | agr34767.notnet.002 | agr34767.notnet.net.002 | agr34767.notnet.Gross.002 | statement.notnet.Gross.002 |
      | agr34767.notnet.003 | agr34767.notnet.net.003 | agr34767.notnet.Gross.003 | statement.notnet.Gross.003 |
      | agr34767.notnet.004 | agr34767.notnet.net.004 | agr34767.notnet.Gross.004 | statement.notnet.Gross.004 |
      | agr34767.notnet.005 | agr34767.notnet.net.005 | agr34767.notnet.Gross.005 | statement.notnet.Gross.005 |

