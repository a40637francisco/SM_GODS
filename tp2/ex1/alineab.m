function alineab 
    %Vai ser usado como crit�rio de sele��o a taxa de compress�o.
    bestPredictorForBird = getBetterPredictor('bird.raw',5,8);
    bestPredictorForCamera = getBetterPredictor('camera.raw',5,8);
    fprintf('bird.raw best predictor = %d \n',bestPredictorForBird);
    fprintf('camera.raw best predictor = %d \n',bestPredictorForCamera);
end