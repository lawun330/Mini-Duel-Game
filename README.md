# Mini-Duel-Game
Play a turn-based interactive duel game through cmd. The user can 
- play with a bot
- play with another user
- watch two bots play

To win a round, the user must reduce the opponent's HP to zero. 
To win a game, the user must win at least 3 rounds.

In each round, there will be indefinite turns. 
In each turn, a user can choose an action out of four options-
1. Deal small damage immediately
2. Harvest energy
3. Deal massive damage (required a certain amount of energy)
4. Immediately surrender

To reduce the biased tendency to collect energy, a rule is implemented. If both players decide to collect energy, no one will get it and both will be self-harmed (reduce both players' HPs).

At the end of the game, the user is asked to make a log file or not. The log file will include the actions of a winning player in a particular round. Note that it is not "a particular game". Example- If player A wins the game by winning 3 rounds while player B loses the game but wins 2 rounds, the log file will note the moves of both player A and B of their respective victory.
