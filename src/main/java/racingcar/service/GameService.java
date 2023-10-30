package racingcar.service;

import racingcar.domain.Car;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import racingcar.view.InputView;
import camp.nextstep.edu.missionutils.Randoms;

public class GameService {
    private List<Car> cars = new ArrayList<>();
    private Integer tryCount = 0;
    private static final Integer MAX_CAR_NAME_LENGTH = 5;
    private static final Integer MAX_RANDOM_NUMBER = 9;
    private static final Integer MIN_RANDOM_NUMBER = 0;

    public void initGame() {
        // carNames를 입력받아 cars를 초기화
        String rawInputStringCarNames = InputView.requestInputCarNames();
        Set<String> carNames = convertInputToCarNameSet(rawInputStringCarNames);
        for(String carName : carNames) {
            cars.add(new Car(carName));
        }

        // tryCount를 입력받아 tryCount를 초기화
        String rawInputStringTryCount = InputView.requestInputTryCount();
        tryCount = convertInputToTryCount(rawInputStringTryCount);
    }

    public void playGame() {
        for(int i = 0; i < tryCount; i++) {
            for(Car car : cars) {
                Integer randomNumber = Randoms.pickNumberInRange(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
                car.move(randomNumber);
            }
        }
    }

    public void endGame() {
        // TODO: 게임을 종료하는 메소드
    }

    private Set<String> convertInputToCarNameSet(String rawInputString) {
        Set<String> carNameSet = new HashSet<>();
        String[] splitedInputString = rawInputString.split(",");

        // 올바른 입력인지 검증
        for(String carName : splitedInputString) {
            if(carName.length() > MAX_CAR_NAME_LENGTH) {
                throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다.");
            }

            if(carNameSet.contains(carName)) {
                throw new IllegalArgumentException("중복된 자동차 이름이 존재합니다.");
            }

            carNameSet.add(carName);
        }

        return carNameSet;
    }

    private Integer convertInputToTryCount(String rawInputString){
        Integer tryCount;

        try{
            tryCount = Integer.parseInt(rawInputString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시도 횟수는 숫자만 입력 가능합니다.");
        }

        if(tryCount < 1){
            throw new IllegalArgumentException("시도 횟수는 1 이상만 가능합니다.");
        }

        return tryCount;
    }
}
