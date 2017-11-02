# Min-sum algorithm for maximum-weight-matching problem.
Program for visualizing min-sum belief propagation algorithm in scope of Graphical Models course at Skoltech.

Algorithm is based on http://web.mit.edu/devavrat/www/maxprod-matching.pdf paper. Repository contains working implementations of both original and simlified versions of the algorithm.
Executable .jar file with instructions of how to run it can be downloaded using this [link](https://drive.google.com/open?id=0Bz7Svh1jQ-KAUno4TzFSN1IwQ3M).

Graph visualization uses [GraphStream library](http://graphstream-project.org) release 1.3.

## Problem statement
Let <img src="https://latex.codecogs.com/gif.latex?K_{n,&space;n}&space;=&space;(V_1,&space;V_2,&space;E)" title="K_{n, n} = (V_1, V_2, E)" /> be a bipartite graph with <img src="https://latex.codecogs.com/gif.latex?V_1=\{\alpha_1,\alpha_2,&space;...,&space;\alpha_n\},&space;V_2=\{\beta_1,\beta_2,&space;...,&space;\beta_n\},&space;(\alpha_i,&space;\beta_j)\in&space;E" title="V_1=\{\alpha_1,\alpha_2, ..., \alpha_n\}, V_2=\{\beta_1,\beta_2, ..., \beta_n\}, (\alpha_i, \beta_j)\in E" /> for <img src="https://latex.codecogs.com/gif.latex?1\leq&space;i,j\leq&space;n" title="1\leq i,j\leq n" />. For each edge of the graph <img src="https://latex.codecogs.com/gif.latex?e_{i,j}=(\alpha_i,\beta_j)" title="e_{i,j}=(\alpha_i,\beta_j)" /> is assigned a weight <img src="https://latex.codecogs.com/gif.latex?w_{ij}\in\mathbb{R}" title="w_{ij}\in\mathbb{R}" />. 

Let's denote <img src="https://latex.codecogs.com/gif.latex?\pi&space;=&space;\{\pi(1),&space;\pi(2),&space;...&space;,&space;\pi(n)\}" title="\pi = \{\pi(1), \pi(2), ... , \pi(n)\}" /> as a permutation of numbers from 1 to n. Then we call set of n edges <img src="https://latex.codecogs.com/gif.latex?\{e_{1\pi(1))}...,e_{n\pi(n))}\}" title="\{e_{1\pi(1))}...,e_{n\pi(n))}\}" /> a matching of graph. Weight of this matching is defined as <img src="https://latex.codecogs.com/gif.latex?W_\pi=\sum_{i=1}^{n}w_{i\pi(i)}" title="W_\pi=\sum_{i=1}^{n}w_{i\pi(i)}" />. The WMW problem is to find a matching that maximizes this weight.




Example of running program for a (7, 7) graph (since it is quite hard to depict all the 49 edges properly, at the end of animation I rearrange graph nodes a bit so it's easier to read).
![Example of running program](http://g.recordit.co/77AMz4AN3b.gif)
