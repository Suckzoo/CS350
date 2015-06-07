[README]
This file contains notation of analysis stated in the file, overall_analysis.csv.

- method metrics
eV : essential complexity
how ill-structured of a method's control flow is. 1<=eV<V(g)

iV : design complexity
minimum number of tests necessary to exercise the integration of the methods (1<=iV<=V(g))

V' : extended cyclomatic complexity of each non-abstract method
1 + number of branch point + number of and/or operations

V : cyclomatic complexity
1 + number of branch point

BRANCH : number of branch point in a method

- class metrics
OCavg : average cyclomatic complexity of a class
WMC : total cyclomatic complexity of a class

- project metrics
METH : number of methods
C : number of classes
CCavg : average cyclomatic complexity of whole project (non-abstract)
CCtot : total cyclomatic complexity of whole project (non-abstract)
LOC : total line of code of whole project