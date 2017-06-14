function alineab 
    %Vai ser usado como critério de seleção a taxa de compressão.
    bestPredictorForBird = getBetterPredictor('bird.raw',5,8);
    bestPredictorForCamera = getBetterPredictor('camera.raw',5,8);
    fprintf('bird.raw best predictor = %d \n',bestPredictorForBird);
    fprintf('camera.raw best predictor = %d \n',bestPredictorForCamera);
end