package monad;


import java.util.Optional;

public class OptionalMonad {

    public static void main(String[] args) {

        Optional<Integer> maybeInteger = Optional.of(1);

// Function that takes Integer and returns Optional<Integer>
        Optional<Integer> maybePlusOne = maybeInteger.flatMap(n -> Optional.of(n + 1));

// Function that takes Integer and returns Optional<String>
        Optional<String> maybeString = maybePlusOne.flatMap(n -> Optional.of(n.toString()));

    }

}
