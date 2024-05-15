# Mini Duel Game
Play a turn-based interactive duel game through the CLI. The game may be further developed using graphics. 
The game has three modes:
1. play with a bot
2. play with another player
3. watch two bots play

To win a round, a player must reduce the opponent's HP to zero. 
To win a game, a player must win at least three rounds.

In each round, there will be indefinite turns. 
In each turn, a player can choose an action out of four options-
1. Deal minor damage
2. Harvest energy
3. Deal massive damage (required a certain amount of energy)
4. Immediately surrender

To balance using minor and massive damage, the following rule is implemented. If both players decide to gather energy at the same turn, both of their HPs are reduced without any gain in their energy bars.

At the end of the game, a question is asked whether to create a log file. The log file will include the actions of a winning player in a particular round. Note that it is not <b>a particular game</b>. 
_Example: If player A wins by winning three rounds while player B loses the game but wins two rounds, the log file will note the moves of both player A and B of their respective victory._
