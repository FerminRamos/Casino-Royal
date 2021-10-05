# Casino-Royal, Overview
Is a command line-based game in which the user has the option of playing one of four popular casino games: Big Six Wheel, Slot Machine, Roulette, or Blackjack

# Entering the Casino
Just like any physical casino, this program requires the user to enter the amount of money he/she wants to convert into chips. The program will automatically keep track of a user's chip amounts. The user has the option of playing multiple rounds of a single game or multiple games, as long as they have enough money (or chips) to keep on going. The will not have to exit the casino (or program) to enter into a different game. 

# Big Six Wheel
Big Six wheel is a luck-based game in which the user can bet any quantity of chips (as long as they can afford the quantity) on any symbol on the wheel. The wheel has symbols: 1, 2, 5, 10, 20, J (joker). If the wheel lands on the selected symbol, the user wins. Else, the user loses their betting quntity.  

# Slot Machine
Slot Machine is a luck-based game in which the user can bet 5 chips per transaction. They will pull a lever and win according to the combination of icons displayed in the slot machine.
| Icon Frequency        | Payout Rate (in chips) |
|  :----------          |  :------------------:  |
| 2 Horseshoes          |         pays 10        |
| 2 Horseshoes + 1 Star |         pays 15        |
| 3 Spades              |         pays 25        |
| 3 Diamonds            |         pays 35        |
| 3 Hearts              |         pays 45        |
| 3 Liberty Bells       |         pays 55        |

*(Note that the payout rate includes the 5 chips lost to play the game. Net winnings is not "payout rate + 5 initial chips".)*

If no match is outputed on the slot machine, then the player loses their 5 chips. 

# Roulette
Roulette is a luck-base game in which the user can make a bet any quantity of chips (as long as they can afford the quantity) on any number on the board. The roulette board has numbers 1-36. The user can place the following bets:
| Possible Bets |
| ------------- |
| Straight |
| Split |
| Street |
| High or Low |
| Red or Black |
| Odd or Even |
| Dozen |
| Column |


# Blackjack
Blackjack is a tactics based game in which the user can bet any quantity of chips (as long as they can afford the quantity). The objective of the game is to 
draw as many cards needed to reach 21 without going actually going over 21. If the user goes over 21, they automatically lose the round. This is called a 
"bust". Cards are assigned points according to their number (symbol cards equal 10). List of cards and their respective point value listed below. A winner 
is not guaranteed during each round. It is entirely possible for both the user and the house (program bot) to both lose. The house (program bot) will continue 
to bet until his hand surpasses 17. 

| Card # | Value |
| :---:  | :---: |
| #1     |1 or 11|
| #2     |   2   |
| #3     |   3   |
| #4     |   4   |
| #5     |   5   |
| #6     |   6   |
| #7     |   7   |
| #8     |   8   |
| #9     |   9   |
| #10    |   10   |
| King   |   10   |
| Queen   |   10   |
| Joker   |   10   |

There are a finite number of outcomes in Blackjack. The user's profits will be depend entirely on which outcome occurs. Here is a list of all possible 
outcomes in the game.

| Situation | Outcome |
| :-------  | :-----  |
| User's hand > House hand, and neither busts| Return: User's Original Bet * 3 |
| User's hand = House hand, and neither busts| Return: User's Original Bet     |
| User's hand < House hand, and neither busts| Return: nothing                 |
| House busts, and user doesn't bust         | Return: User's Original Bet * 2 |
| Useer busts (house skips turn betting)     | Return: nothing                 |
| User surrenders                            | Return: User's Original Bet / 2 |


