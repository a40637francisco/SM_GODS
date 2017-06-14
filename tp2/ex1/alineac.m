function alineac
    compRatioP1Img1 = GolombDecomposition('circles.raw',3,1);
    compRatioP2Img1 = GolombDecomposition('circles.raw',3,4);
    compRatioPLSImg1 = GolombDecomposition('circles.raw',3,8);

    compRatioP1Img2 = GolombDecomposition('finger.raw',3,1);
    compRatioP2Img2 = GolombDecomposition('finger.raw',3,4);
    compRatioPLSImg2 = GolombDecomposition('finger.raw',3,8);
    compRatioDirectImg2 = GolombDecomposition('finger.raw',3,0);
    
    fprintf('circles.raw JPEG 1 = %.2f %%\n',compRatioP1Img1);
    fprintf('circles.raw JPEG 4 = %.2f %%\n',compRatioP2Img1);
    fprintf('circles.raw JPEG LS = %.2f %%\n',compRatioPLSImg1);

    fprintf('finger.raw JPEG 1 = %.2f %%\n',compRatioP1Img2);
    fprintf('finger.raw JPEG 4 = %.2f %%\n',compRatioP2Img2);
    fprintf('finger.raw JPEG LS = %.2f %%\n',compRatioPLSImg2);
    fprintf('finger.raw Direct = %.2f %%\n',compRatioDirectImg2);
end