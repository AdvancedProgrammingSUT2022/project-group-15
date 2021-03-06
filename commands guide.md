# Commands Guide (Phase 1)

The order of flags doesn't matter in each command below that contains dashes

## Commands available in every menu

1) menu show-current
2) menu exit
3) menu enter **_menu_name_** <br> **_menu_name_** can be "login menu", "main menu", "profile menu", and "game menu"

## Login Menu Commands

1) user create -u/--username **_username_** -p/--password **_password_** -n/--nickname **_nickname_**
2) user login -u/--username **_username_** -p/--password **_password_**

## Main Menu Commands

1) user logout
2) play game -p1/--player1 **_fist player username_** -p2/--player2 **_second player username_** ...

## Profile Menu Commands

1) profile change -n/--nickname **_nickname_**
2) profile change -p/--password -c/--current **_current password_** -n/--new **_new password_**

## Game Menu Commands

#### <li> General Commands

1) info **_name of panel_** <br> **_name of panel_** can be "research", "units", "cities", "diplomacy", "victory", "
   demographics", "notifications", "military", "economic", "deals" <br> (some of them are not for phase 1)
2) buy new technology : **_technology name_** <br> You can access the list of technology names using the previous
   command
3) select unit combat **_x_** **_y_**
4) select unit noncombat **_x_** **_y_**
5) select city **_city name_**
6) select city **_x_** **_y_**
7) next turn

#### <li> Unit Commands

8) unit moveto **_x_** **_y_** XXXXX
9) unit sleep XXXXX
10) unit alert XXXXXXx
11) unit fortify XXXXXXXXX
12) unit garrisonXXXXXXX
13) unit setup ranged XXXXXXXXXXX
14) unit fortify heal XXXXXXX
15) unit found city XXXXX
16) unit cancel mission XXXXX
17) unit wake XXXXX
18) unit delete XXXXX
19) unit attack **_x_** **_y_** XXXXX
20) unit build **_improvement name_** XXXXX
21) unit remove jungle or swamp XXXXXX
22) unit remove route XXXXX
23) unit repair XXXXXXXXX
24) unit pillageXXXXXXXXX

#### <li> City Commands

25) city remove citizen from work on **_x_** **_y_**
26) city lock citizen on **_x_** **_y_**
27) city choose production **_unit name_** XXXXXXX
28) city purchase unit **_unit name_** XXXXXXXXXXXX
29) city buy hex **_x_** **_y_**
30) city attack **_x_** **_y_**   xxxxxxx  
(choosing building and buying building added in phase 2) XXXXXXX

#### <li> Map Commands

31) map move **_direction_** **_amount_** <br> **_direction_** can be "left", "right", "up", "down"
32) map show **_x_** **_y_**
33) map show **_city name_**

#### <li> Cheat Commands

34) cheat increase --turn **_amount_**
35) cheat increase --gold **_amount_**
36) cheat increase --science **_amount_**
37) cheat increase --citizens **_amount_** <br> Adds "amount" citizens to each city
38) cheat increase --score **_amount_**
39) cheat open all technologies <br> It automatically opens all features, improvements, resources, and units
40) cheat win
41) cheat make the whole map visible
42) cheat found city on **_x_** **_y_**
43) cheat increase health of selected unit
44) cheat increase power of selected unit
