Feature: LetterCompare
  Send letter

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration
    And have system preferences sys.pref.letter
    And approve all statements in approvals manager

  @kentgu
  Scenario Outline: letter comparison - margin letter
    When search agreement <agr.search>
    And view completed agreement statement <agr.statement>
    And Agreement Admin - manual send letter <margin.letter>
    Examples:
      | agr.search      | agr.statement         | margin.letter                     |
      | agr.search.501  | agr.searchresult.501  | margin.letter.501                 |
      | agr.search.502  | agr.searchresult.502  | margin.letter.502                 |
      | agr.search.503  | agr.searchresult.503  | margin.letter.503                 |
      | agr.search.504  | agr.searchresult.504  | margin.letter.504                 |
      | agr.search.505  | agr.searchresult.505  | margin.letter.505                 |
      | agr.search.506  | agr.searchresult.506  | margin.letter.506                 |
      | agr.search.507  | agr.searchresult.507  | margin.letter.507                 |
      | agr.search.1001 | agr.searchresult.1001 | margin.letter.1001                |
      | agr.search.1002 | agr.searchresult.1002 | margin.letter.1002                |
      | agr.search.1003 | agr.searchresult.1003 | margin.letter.1003                |
      | agr.search.1004 | agr.searchresult.1004 | margin.letter.1004                |
      | agr.search.1005 | agr.searchresult.1005 | margin.letter.1005                |
      | agr.search.1006 | agr.searchresult.1006 | margin.letter.1006                |
      | agr.search.1007 | agr.searchresult.1007 | margin.letter.1007                |
      | agr.search.1008 | agr.searchresult.1008 | margin.letter.1008                |
      | agr.search.1009 | agr.searchresult.1009 | margin.letter.1009                |
      | agr.search.1010 | agr.searchresult.1010 | margin.letter.1010                |
      | agr.search.1011 | agr.searchresult.1011 | margin.letter.1011                |
      | agr.search.1012 | agr.searchresult.1012 | margin.letter.1012                |
      | agr.search.1013 | agr.searchresult.1013 | margin.letter.1013                |
      | agr.search.1014 | agr.searchresult.1014 | margin.letter.1014                |
      | agr.search.1025 | agr.searchresult.1025 | margin.letter.1025                |
      | agr.search.1029 | agr.searchresult.1029 | margin.letter.vm.1029             |
      | agr.search.1029 | agr.searchresult.1029 | margin.letter.im.1029             |
      | agr.search.1509 | agr.searchresult.1509 | margin.letter.callrecall.1509     |
      | agr.search.1509 | agr.searchresult.1509 | margin.letter.call.1509           |
      | agr.search.1509 | agr.searchresult.1509 | margin.letter.recall.1509         |
      | agr.search.1510 | agr.searchresult.1510 | margin.letter.deliveryreturn.1510 |
      | agr.search.1510 | agr.searchresult.1510 | margin.letter.delivery.1510       |
      | agr.search.1510 | agr.searchresult.1510 | margin.letter.return.1510         |
      | agr.search.1511 | agr.searchresult.1511 | margin.letter.1511                |
      | agr.search.1512 | agr.searchresult.1512 | margin.letter.1512                |
      | agr.search.1513 | agr.searchresult.1513 | margin.letter.1513                |
      | agr.search.1514 | agr.searchresult.1514 | margin.letter.1514                |
      | agr.search.1516 | agr.searchresult.1516 | margin.letter.1516                |
      | agr.search.1517 | agr.searchresult.1517 | margin.letter.1517                |

  @kentgu
  Scenario Outline: letter comparison - interest letter
    When navigate to interest manager search page
    And Interest Manager - search interest event by <interest.search>
    And Interest Manager - switch to <tab.name> tab
    And Interest Manager - create letters <intere.letter> for event <interest.event>
    Examples:
      | interest.search      | tab.name           | intere.letter      | interest.event    |
      | interest.search.1015 | Pay                | margin.letter.1015 | interest.vm.1015  |
      | interest.search.1015 | Pay                | margin.letter.1015 | interest.im.1015  |
      | interest.search.1015 | Receive            | margin.letter.1015 | interest.vm.1015  |
      | interest.search.1015 | Receive            | margin.letter.1015 | interest.im.1015  |
      | interest.search.1016 | Capitalise pay     | margin.letter.1015 | interest.vm.1016  |
      | interest.search.1016 | Capitalise pay     | margin.letter.1015 | interest.im.1016  |
      | interest.search.1016 | Capitalise receive | margin.letter.1015 | interest.vm.1016  |
      | interest.search.1016 | Capitalise receive | margin.letter.1015 | interest.im.1016  |
      | interest.search.1504 | Pay                | margin.letter.1015 | interest.net.1504 |
      | interest.search.1504 | Receive            | margin.letter.1015 | interest.net.1504 |
      | interest.search.1508 | Capitalise pay     | margin.letter.1015 | interest.vm.1508  |
      | interest.search.1508 | Capitalise pay     | margin.letter.1015 | interest.im.1508  |
      | interest.search.1508 | Capitalise receive | margin.letter.1015 | interest.vm.1508  |
      | interest.search.1508 | Capitalise receive | margin.letter.1015 | interest.im.1508  |

  @kentgu
  Scenario: letter comparison - 1017
    When search agreement agr.search.1017
    And view completed agreement statement agr.searchresult.1017
    And edit asset summary info
    And view asset type asset.bond.jpy agreement asset Page
    And resend confirmation letter for booking asset.bond.jpy
    And click back button to previous page
    And view asset type asset.bond.gbp agreement asset Page
    And resend confirmation letter for booking asset.bond.gbp
    And click back button to previous page
    And view asset type asset.equity.jpy agreement asset Page
    And resend confirmation letter for booking asset.equity.jpy
    And click back button to previous page
    And view asset type asset.equity.gbp agreement asset Page
    And resend confirmation letter for booking asset.equity.gbp
    And click back button to previous page
    And view asset type asset.cash.usd agreement asset Page
    And resend confirmation letter for booking asset.cash.usd
    And click back button to previous page
    And view asset type asset.cash.eur agreement asset Page
    And resend confirmation letter for booking asset.cash.eur

  @kentgu
  Scenario: letter comparison - 2004
    When search agreement agr.search.2004
    And view completed agreement statement agr.searchresult.2004
    And select agreement agr.fun1.2001 in umbrella list
    And edit asset summary info
    And view asset type asset.bond.gbp agreement asset Page
    And add call bookings - statement booking booking.2001.1
    And add call bookings - statement booking booking.2001.2
    And select agreement agr.fun2.2002 in umbrella list
    And add call bookings - statement booking booking.2002.1
    And add call bookings - statement booking booking.2002.2
    And select agreement agr.fun3.2003 in umbrella list
    And add call bookings - statement booking booking.2003.1
    And add call bookings - statement booking booking.2003.2
    And navigate to settlement status page
    And settlement status - search settlement status settlement.search.2004
    And settlement status - show asset settlement.asset.2004 details
    And settlement status - approve all pending in settlement status search result
    And settlement status - approve all authorised in settlement status search result
