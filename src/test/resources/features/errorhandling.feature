Feature: Raise an inquiry for a private movement within the same town

  Private persons can inquiry for a private movement within the same town. When a person inquiry such a movement
  it get a reference number back and will contacted within 24 hours. The reference number is important and has to be
  used by every communication either personally or via the system.

  Scenario: Raise an inquiry successfully
    Given Alexander Berger wants to move from Alserstraße. 30/3, 1080 Wien to Innstraße 7/1, 4020 Linz
    And wants to be contacted via telephone number +4319453933
    When he inquires support for the movement
    Then he gets an error back because the cities are not the same!
    And the information she will get contacted within the next 24 hours
