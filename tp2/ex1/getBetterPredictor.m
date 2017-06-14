function [predictor] = getBetterPredictor (file,M,nrOfPredictors)
    arrayOfCompRatios = zeros(nrOfPredictors,1);
    for i=1 : nrOfPredictors
        arrayOfCompRatios(i) = GolombDecomposition(file,M,i);
        %fprintf('comp ratio of %d = %.2f %% \n', i, arrayOfCompRatios(i));
    end
    [~,I] = min(arrayOfCompRatios);
    predictor = I;
end