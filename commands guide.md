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
1) info **_name of panel_** <br> **_name of panel_** can be "research", "units", "cities", "diplomacy", "victory", "demographics", "notifications", "military", "economic", "deals"
2) buy new technology : **_technology name_** <br> You can access the list of technology names using the previous command
3) select unit combat **_x_** **_y_**
4) select unit noncombat **_x_** **_y_**
5) select city **_city name_**
6) select city **_x_** **_y_**
#### <li> Unit Commands
7) unit moveto **_x_** **_y_**
8) unit sleep
9) unit alert
10) unit fortify
11) unit garrison
12) unit setup ranged
13) unit fortify heal
14) unit found city
15) unit cancel mission
16) unit wake
17) unit delete
18) unit attack **_x_** **_y_**
19) unit build **_improvement name_**
20) unit remove jungle
21) unit remove route
22) unit repair
#### <li> City Commands
23) city remove citizen from work on **_x_** **_y_**
24) city lock citizen on **_x_** **_y_**
25) city choose production **_unit name_**
26) city purchase unit **_unit name_**
27) city buy hex **_x_** **_y_**
28) city attack **_x_** **_y_**
#### <li> Map Commands
29) map move **_direction_** **_amount_** <br> **_direction_** can be "left", "right", "up", "down"
30) map show **_x_** **_y_**
31) map show **_city name_**
32) next turn