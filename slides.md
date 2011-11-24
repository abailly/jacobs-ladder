% Jacob's Ladder
% Arnaud Bailly - Christophe Thibaut
% 01/12/2011

# Agenda

- Intro
- Category Theory - Part I
- Interlude I
- Category Theory - Part II
- Interlude II
- Discussion
- Outro

# Introduction

# Goal of this Session

4 reasons to care about Category Theory:

- Because it's so cool
- Because it's what *Functional Programming* is built on
- Because it's so cryptic
- Because it's so useful

# What is Category Theory?

- A mathematical formalism created by *S.Eilenberg* and *J.MacLane* in the
  40's to unify various phenomena scattered throughout various
  fields of mathematics.  
- Sometimes dubbed abstract nonsense by more down-to-earth
  mathematicians...  
- Developed in the 50's and 60's in the field of algebraic topology by
  people like *A.Grothendiek*, and since the 70's in Computer science to
  model various things related to computability 
   
# So What is a Category?

- A kind of structure which is at the heart of Cat.Th.: _Look handout
  for details and references_ 
- Here is a faithful analogy: A category is the transitive closure of a
  (possibly infinite) *directed graph*, with some additional
  properties
- Some categories are special (eg. *Set*)

# "Well-known" Categories 

- **Set**, **Mon**, **CPO**...: The category of sets with total functions or morphisms
- **Hask**: Category of Haskell types
- **CAM**: Categorical abstract machine, a practical compilation scheme for ML languages

# Constructions In Categories

- **Product**: Allows *tupling* of objects and arrows
- **Sums**: Disjoint unions of objects and arrows
- **0**: Initial object, one and only one arrow from 0 to any other object
- **1**: Terminal object, one and only one arrow from any other object to 1
- Using 1 as a *selector* for "elements" in objects (eg. members of a set)

# Related Computing Concepts

- **Product** ⟶ Define records and structures, apply functions on them
    as a group or on invidual elements of the structure
- **Sums** ⟶ Error handling, turning partial functions to total ones,
    case analysis
- **0**, **1**  ⟶ Special values (*null*, *undefined*)
    
# Interlude I

# FooBarQix 

- Construct (or _deconstruct_) the function **FooBarQix** as proposed
  on [Code Story](http://www.code-story.net/2011/11/16/foobarqix.html)
- Use only arrows and objects
- Assume we work in a well-behaved category
- Refine a function till you drop

# More Constructions

# Exponential

- Representing *function abstraction* in a category
- Let A, B be objects, we can form $B^A$ the *exponential object* and a
  function eval : $B^A  × A → B$ s.t. for any $f: C × A → B$, f
  factorizes uniquely through eval:
  
>      f = ev ∘ (λf × ι)

# Exponential (bis)

- This also says that one can form  $λf: C → B^A$ the function that
  that transforms a two-argument function (here $C × A → B$) into a
  function from one argument to another function
- This operation is known as _currying_

# Pullbacks, Equalizers

- *Pullback*: An object and 2 arrows defining a (kind of) _constrained
   product_. In **Set**, intersection of 2 sets arise as a pullback
- *Equalizer*: An object and 1 arrow defining a common attribute of 2
   arrows. In **Set**, the set of elements with same image under 2
   functions $f: A → B$ and $g: A → B$.

# Categorical Abstract Machine 

- Representing general computations within a category : allows compiling *strict* functional programs
into an intermediate form suitable for compilation to low-level
languages (eg. C, assembly...)
- Rests on the concept of a cartesian closed category: A category with
  all limits and exponentiation.

# Even More Constructions

# Constructions Over Categories

- *Let's go Up the ladder*: applying categorical concepts to categorical constructs
- If you are curious: Yes, the *category of categories* exists! (with some restrictions...)

# Functors

- Mappings from objects to objects s.t. composition of functions is preserved 

> $f: a → b ⇒ Ff : Fa → Fb$

# Functors (contd.)

> *Down the ladder*

- **Functors** give rise to parametric data structures, or *type
    constructors* you are familiar with: **Lists**, **Sets**,
    **Streams**, **Array** ...

# Applicative Functors

- <$> : $a → b → (f a → f b)$ : this is standard "functor
  application"
- <*> : $f (a → b) → (f a → f b)$ : allows working on multiple arity
  functions, _push argument application inside functor_

# Applicative Functors (contd.)

> *Down the ladder*: Composing asynchronous computations

- Given a value of type A, construct the *Future of A* namely $F A$ (a
  functor): This is any A _in the future_
- *fmap* ⟶ Transform any function $f : A → B$ into a function on futures $F f : F A → F B$
- *apply* ⟶ Tranform a binary function f : A → B → C into a function
on futures: g = f <$> a : F (B → C) ⟶ g <*> b : F C

# Monads

- 2 different presentations: Kleisli triples *and* monads
- **Kleisli triples**: A functor M, an arrow *return* : $A → M A$, an arrow *bind* : $(A → M B) → (M A → M B)$
- **Monads**: A functor M, A natural transformation *unit* : $A → M A$, A natural transformation *join* :  $M M A → M A$

# Monads (bis)

- These representations are equivalent:

>        join = bind id
>      bind f = join ∘ (fmap f)
>      fmap f = bind (unit ∘ f)

- Monads are a  way to express *computations over values* categorically
- Monad tutorials is a _genre_ in itself within FP community!

# Interlude II

# The Composed Method pattern

- Reference is
  [Neal Ford](http://www.ibm.com/developerworks/java/library/j-eaed4/index.html)'s
  article: It applies *Composed Method* pattern and *Single Level
  Abstraction Principle* to standard JDBC reading problems
- How to compose composed methods with transactionality? Proposed
  refactoring falls short of allowing *composition of method within a
  transactional context* 
- Can we do better?

# LINQ Providers

- [Erik Meijer about LINQ](http://cacm.acm.org/magazines/2011/10/131398-the-world-according-to-linq/fulltext)
- LINQ uses monads' *flatMap* (aka. *bind*) to construct **queries**
  over various datatypes, including relational structures 
- The various keywords of the SQLish language (SELECT, FROM, WHERE)
  are just instances of (a → m b) which are chained in the monad

# This is not the end...

# Natural transformations

- Mappings **between functors**
- A transformation need not be an isomorphism (ie. invertible), it may
  "forget" some aspects of the functors it maps 
- *Down the ladder*: Functions on lists that change the "shape" of the
  list without changing the elements in it (reverse, inits, tails...) 
- I/O on HTTP messages, REST interface of a service

# Duality

> The slogan is: Reverse all arrows!
	
- For each construction in Cat.Th., there is a *dual* construction which arises when one "reverse the arrows"
- Given a construction _foo_, then the dual is _co-foo_
- Product ↣ Sum (Co-Product)
- Terminal object ↣ Initial object
- Pullback ↣ Pushout
- Monad ↣ Co-Monad

# Conclusion

Relating categorical thinking to agile design principles:

- *Simple design*: only arrows and (abstract) objects, no patterns, no
  frameworks, no fancy coding tricks
- *No BDUF*: Designing functions first: What will you do with your
  data/objects?, refrain from defining the implementation first,
  abstract from details to prevent data lock-in
- *Removing duplication*: Generalizing to higher-levels allow DRY to
  apply not only on functions but also on control structures and
  "shape" of the system 
- Focusing on the *Domain*: First define core arrows, those that are
  part of the domain you are working on ; then apply functors and
  transformations to *lift* from core domain to a more complex
  behaviours (error handling, logging, interfacing with the outside world)

# Caveats

- Do not talk of this conference to your local mathematician, its hair
  might go white earlier than expected! 
- True Cat.Th. is insanely complex, it has connections to all of
  mathematics (logic, topology, algebra, analysis) and is very
  abstract with lot of weird constructions

# References (soft.)

- [Haskell](http://haskell.org): pioneer in applied cat.th. 
- [Scalaz](http://code.google.com/p/scalaz): Library in scala, provide a wealth of categorical
  constructions for scala programs
- [Category in java](): representing cat.th. in Java

# References (articles)

- [Applicatives functors](http://hseeberger.wordpress.com/2011/01/31/applicatives-are-generalized-functors/)
- [Arithmetic & FP](http://aabs.wordpress.com/2008/05/29/functional-programming-lessons-from-high-school-arithmetic/)
- [Essence of the Iterator Pattern](http://etorreborre.blogspot.com/2011/06/essence-of-iterator-pattern.html)
- [Notions of Computations and Monads](http://www.disi.unige.it/person/MoggiE/ftp/ic91.pdf)
- [Scala is like EJB 3](http://blog.joda.org/2011/11/scala-feels-like-ejb-2-and-other.html?showComment=1321986456857#c1028446403700470017)
- [The Categorical Abstract Machine]()

# Credits

- [Bath's Jacob's Ladder](http://en.wikipedia.org/wiki/File:Himnastigi.jpg)
- [Augsburg Barfüßerkirche](http://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Augsburg_Barf%C3%BC%C3%9Ferkirche_013.jpg/822px-Augsburg_Barf%C3%BC%C3%9Ferkirche_013.jpg)
- [Drawing commutative diagrams in Latex with TikZ](http://www.felixl.de/commu.pdf)
