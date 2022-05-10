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
   demographics", "notifications", "military", "economic", "deals"
2) buy new technology : **_technology name_** <br> You can access the list of technology names using the previous
   command
3) select unit combat **_x_** **_y_**
4) select unit noncombat **_x_** **_y_**
5) select city **_city name_**
6) select city **_x_** **_y_**
7) next turn

#### <li> Unit Commands

8) unit moveto **_x_** **_y_**
9) unit sleep
10) unit alert
11) unit fortify
12) unit garrison
13) unit setup ranged
14) unit fortify heal
15) unit found city
16) unit cancel mission
17) unit wake
18) unit delete
19) unit attack **_x_** **_y_**
20) unit build **_improvement name_**
21) unit remove jungle
22) unit remove route
23) unit repair

#### <li> City Commands

24) city remove citizen from work on **_x_** **_y_**
25) city lock citizen on **_x_** **_y_**
26) city choose production **_unit name_**
27) city purchase unit **_unit name_**
28) city buy hex **_x_** **_y_**
29) city attack **_x_** **_y_**

#### <li> Map Commands

30) map move **_direction_** **_amount_** <br> **_direction_** can be "left", "right", "up", "down"
31) map show **_x_** **_y_**
32) map show **_city name_**

#### <li> Cheat Commands

33) cheat increase --turn **_amount_**
34) cheat increase --gold **_amount_**
35) cheat increase --science **_amount_**
36) cheat increase --citizens **_amount_** <br> Adds "amount" citizens to each city
37) cheat increase --score **_amount_**
38) cheat open all technologies <br> It automatically opens all features, improvements, resources, and units
39) cheat win
40) cheat make the whole map visible
41) cheat found city on **_x_** **_y_**
42) cheat dry rivers on **_x_** **_y_**
43) cheat increase health of selected unit