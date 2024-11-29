Angry Birds Clone - CSE 201 Project
Overview
This project is an implementation of the popular Angry Birds game using Java and LibGDX. The game allows players to launch birds from a slingshot to destroy pigs and various structures made from blocks. The goal is to complete each level by destroying all the pigs using a limited number of birds.

Key Features:
Different types of birds with unique abilities (Red Bird, Yellow Bird, Black Bird).
Physics-based gameplay using Box2D for accurate collision and movement.
Various materials like wood, glass, and stone with different durability.
A slingshot with drag-to-launch mechanics for controlling the bird's trajectory.
Levels with progressively harder obstacles and pigs.
Score tracking and level transitions.

Usage
Gameplay:
The game consists of different levels where you must launch birds at pigs and various blocks to destroy them.
You have a limited number of birds in each level.
The bird is placed at the head of the slingshot. You can drag the bird backward to aim and release it to launch.
Birds have different powers (e.g., the Red Bird has standard power, the Yellow Bird accelerates, and the Black Bird explodes).
Each level must be completed by destroying all pigs within the bird limit.

Controls:
Drag: Click and hold to drag the bird backward in the slingshot.
Release: Let go to launch the bird.

Game Mechanics
Slingshot:
The slingshot acts as a catapult that launches birds towards the targets (pigs and blocks).
The bird's launch velocity is determined by how far it is pulled back in the slingshot.
The birds are placed at the head of the slingshot before each launch.
Birds:
Red Bird: Standard bird with average power.
Yellow Bird: Speedy bird with a special power to increase speed upon activation.
Black Bird: Explosive bird that causes an area of effect damage when it hits the target.
Blocks:
Different types of blocks with varying durability:
Wood Block: Breaks easily.
Glass Block: Breaks with higher damage.
Stone Block: Very tough to break.
Pigs:
Pigs are placed on the blocks and must be destroyed to complete the level.
Larger pigs have more health and require more damage to be destroyed.

Game Screens:
Main Menu Screen: The main menu with options to start the game, view settings, or exit.

Level Selection Screen: A screen to choose different levels to play.

Gameplay Screen: The main gameplay screen where you launch birds at structures and pigs.

Pause Screen: A screen to pause the game, restart the level, or return to the main menu.

