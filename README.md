# Min-sum algorithm for maximum-weight-matching problem.
Program for visualizing min-sum belief propagation algorithm in scope of Graphical Models course at Skoltech.

Algorithm is based on http://web.mit.edu/devavrat/www/maxprod-matching.pdf paper. Repository contains working implementations of both original and simlified versions of the algorithm.
Executable .jar file with instructions of how to run it can be downloaded using this [link](https://drive.google.com/open?id=0Bz7Svh1jQ-KAUno4TzFSN1IwQ3M).

Graph visualization uses [GraphStream library](http://graphstream-project.org) release 1.3.

## Problem statement
Let <img src="https://latex.codecogs.com/gif.latex?K_{n,&space;n}&space;=&space;(V_1,&space;V_2,&space;E)" title="K_{n, n} = (V_1, V_2, E)" /> be a bipartite graph with <img src="https://latex.codecogs.com/gif.latex?V_1=\{\alpha_1,\alpha_2,&space;...,&space;\alpha_n\},&space;V_2=\{\beta_1,\beta_2,&space;...,&space;\beta_n\},&space;(\alpha_i,&space;\beta_j)\in&space;E" title="V_1=\{\alpha_1,\alpha_2, ..., \alpha_n\}, V_2=\{\beta_1,\beta_2, ..., \beta_n\}, (\alpha_i, \beta_j)\in E" /> for <img src="https://latex.codecogs.com/gif.latex?1\leq&space;i,j\leq&space;n" title="1\leq i,j\leq n" />. For each edge of the graph <img src="https://latex.codecogs.com/gif.latex?e_{i,j}=(\alpha_i,\beta_j)" title="e_{i,j}=(\alpha_i,\beta_j)" /> is assigned a weight <img src="https://latex.codecogs.com/gif.latex?w_{ij}\in\mathbb{R}" title="w_{ij}\in\mathbb{R}" />. 

Let's denote <img src="https://latex.codecogs.com/gif.latex?\pi&space;=&space;\{\pi(1),&space;\pi(2),&space;...&space;,&space;\pi(n)\}" title="\pi = \{\pi(1), \pi(2), ... , \pi(n)\}" /> as a permutation of numbers from *1* to *n*. Then we call set of *n* edges <img src="https://latex.codecogs.com/gif.latex?\{e_{1\pi(1))}...,e_{n\pi(n))}\}" title="\{e_{1\pi(1))}...,e_{n\pi(n))}\}" /> a matching of graph. Weight of this matching is defined as <img src="https://latex.codecogs.com/gif.latex?W_\pi=\sum_{i=1}^{n}w_{i\pi(i)}" title="W_\pi=\sum_{i=1}^{n}w_{i\pi(i)}" />. The WMW problem is to find a matching that maximizes this weight.

## Algorithm
The implemented algorithm is a [belief propagation](https://web.stanford.edu/~montanar/RESEARCH/BOOK/partD.pdf) message passing algorithm that tries to find maximum a posteriori probability of a distribution defined by a graphical model that expoites innate dependency structure between random variables represented by the nodes and edges in the graph. 
On each iteration of the algorithm each node passes messages to its neighbours, where message is basically a function of a receiving information variable, intuitively representing probability information that a neighbour node have managed to collect from its adjacent nodes on the previous iteration. Then, for each node we estimate its 'beliefs' - likelihoods of each adjacent edge to be present in a matching. For each node <img src="https://latex.codecogs.com/gif.latex?\alpha_i" title="\alpha_i" /> matched edge is an edge with maximum belief. Algorithm makes such iterations until the gained matching has converged. For the more formal details: how the graphical model for this problem is defined, what is the messages initialization point, message passing and belief update rules - please refer to the original paper linked above.

## Implementation
This project was made in order to visulize algorithm (matching choosing part) and check its correctness. In the original paper it was proven that algorithm converges to a right solution in <img src="https://latex.codecogs.com/gif.latex?O(\frac{nw*}{\varepsilon&space;})" title="O(\frac{nw*}{\varepsilon })" /> iterations where <img src="https://latex.codecogs.com/gif.latex?w*" title="w*" /> is a maximum weight in a graph and <img src="https://latex.codecogs.com/gif.latex?\varepsilon" title="\varepsilon" /> is the difference between the weight of the best and the second-best solutions. Firstly, I ran 10000 experiments on randomly generated graphs to check the correctness of the algorithm (for ground truth I used brute force algorithm of *O(n!))*. For each launched experiment the resulting matching was equal to the right one. In order to provide uniqueness of the solution and observability of the correctness on the visualized solution, each edge is initialized with a random integer weight from 0 to <img src="https://latex.codecogs.com/gif.latex?w*" title="w*" />, but at the beggining of the algorithm, very small real number is added.

## Visualization example
After launching the program, you will see the randomly generated bipartite graph with 2n vertices. Each *m* milliseconds as you specified, new iteration of the algorithm will be launched and the resulting matching will be highlighted in red.

Example of running program for a (7, 7) graph (since it is quite hard to depict all the 49 edges properly, at the end of animation I rearrange graph nodes a bit so it's easier to read):
![Example of running program](http://g.recordit.co/77AMz4AN3b.gif)
