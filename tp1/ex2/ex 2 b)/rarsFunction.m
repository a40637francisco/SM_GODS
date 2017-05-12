function [removed_rate]  = rarsFunction(fileSize,fileNumber,filesInitSize)

    removed_rate = (1 - fileSize / filesInitSize(fileNumber) ) * 100;
    ceil(removed_rate)
    
end