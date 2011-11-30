import Test.HUnit
import System.Exit
import System.IO
import Data.List
import System
import System.Exit

fooBarQix :: Int -> String

(•) = (.)
      
fooBarQix n = (plain           • 
               content         • 
               divisor 7 "Qix" • 
               divisor 5 "Bar" • 
               divisor 3 "Foo") (n,"")

plain (n,"") = show n
plain (_,s ) = s

divisor k r (n,out) | n `mod` k == 0 = (n, out ++ r)
                    | otherwise      = (n, out)
  
content :: (Int, String) -> (Int, String)
content (n,s) = (n,s ++ concatMap fbq (show n))
  where
    fbq '3' = "Foo"
    fbq '5' = "Bar"
    fbq '7' = "Qix"
    fbq  c  = ""
    
main = mapM_ putStrLn $ map fooBarQix [1 .. 100]

--- TESTS

tests = TestList [
  fooBarQix 1 ~?= "1",
  fooBarQix 2 ~?= "2",
  fooBarQix 3 ~?= "FooFoo",
  fooBarQix 4 ~?= "4",
  fooBarQix 5 ~?= "BarBar",
  fooBarQix 6 ~?= "Foo",
  fooBarQix 7 ~?= "QixQix",
  fooBarQix 9 ~?= "Foo",
  fooBarQix 10 ~?= "Bar",
  fooBarQix 14 ~?= "Qix",
  fooBarQix 13 ~?= "Foo",
  fooBarQix 15 ~?= "FooBarBar",
  fooBarQix 21 ~?= "FooQix",
  fooBarQix 51 ~?= "FooBar",
  fooBarQix 33 ~?= "FooFooFoo",
  fooBarQix 53 ~?= "BarFoo"
  ]
        
--- bruit pour les tests....
runTests = do counts <- runTest (tests)
              case (errors counts + failures counts) of
                0 -> return ExitSuccess
                n -> return $ ExitFailure n

runTest :: Test -> IO Counts
runTest  t = do (counts, _) <- runTestText (putTextToHandle stderr False) t
		return counts
