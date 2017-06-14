function [compRatio] = GolombDecomposition( file , M ,predictor)
  
    f = dir(file);
    size = f.bytes;

    difFile = getDifImage(file,predictor,size);
        
   [~,nrOfSymbols] = histFile(file,difFile);
   % Retirar os simbolos que ocorrem 0 vezes
   
   %get golomb sequence
   seq = GolombSequence(nrOfSymbols,M);
   relFreq = tabulate(difFile);
   
   [~,order] = sort(relFreq(:,3),'descend');
   sortedRelFreq = relFreq(order,:);
   
   fid = fopen( 'results.txt', 'wt' );
   nbits = 0;
   for i = 1 : length(difFile)
       x = difFile(i,1);
       index = sortedRelFreq(:,1) == x;
       cell = seq(index); 
       if(isempty(cell))
        disp('somethin wrong')
       end
       golombCode = cell{1,1};
       word = num2str(golombCode);
       word = word(find(~isspace(word)));
       fprintf(fid,'%s \n',word);
       nbits = nbits + length(word);
   end
   
   fclose(fid);
   total_bytes = nbits / 8;
   %fprintf('bytes of original file = %10d \n', size);
   %fprintf('bytes of compressed file = %10d \n', total_bytes);
   compRatio = (total_bytes / size)*100;
   %fprintf('compression ratio = %.2f %%\n',compRatio);
   
end


function [resimage] = getDifImage(file,predictor,totalsize)
    % Ler a imagem a partir do ficheiro.
    fin=fopen(file,'r');
    
    I=fread(fin);
    image=reshape(I,[sqrt(totalsize),sqrt(totalsize)]);
    [M,N] = size(image);
    
    filesize = M * N;
    difImage = zeros(filesize,1);
    count = 1;
    for i = 1 : M
        for j = 1 : N    
            %+200 para anular valores negativos.
            if(predictor ~= 0)
                difImage(count) = 200 + (image(j,i) - getPredition(image,predictor,j,i));
            else
                difImage(count) = image(j,i);
            end
            count = count + 1;
        end
    end
    resimage = difImage;
end

function [dif] = getPredition(image,predictor,line,col)
    switch predictor
        case 1
            dif = predictor1(image,line,col); 
        case 2
            dif = predictor2(image,line,col); 
        case 3
            dif = predictor3(image,line,col); 
        case 4
            dif = predictor4(image,line,col);  
        case 5
            dif = predictor5(image,line,col);  
        case 6
            dif = predictor6(image,line,col);  
        case 7
            dif = predictor7(image,line,col); 
        case 8
            dif = predictor8(image,line,col); 
        otherwise
            return;
    end
end

function [out] = predictor1(image,line,col)
    %Predictor 1 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        A = image(line,col-1);
        out = A; 
    end
end

function [out] = predictor2(image,line,col)
    %Predictor 2 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        B = image(line-1,col);
        out = B; 
    end
end

function [out] = predictor3(image,line,col)
    %Predictor 3 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        C = image(line-1,col-1);
        out = C; 
    end
end

function [out] = predictor4(image,line,col)
    %Predictor 4 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        A = image(line,col-1);
        B = image(line-1,col);
        C = image(line-1,col-1);
        out = A + B - C; 
    end
end

function [out] = predictor5(image,line,col)
    %Predictor 5 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        A = image(line,col-1);
        B = image(line-1,col);
        C = image(line-1,col-1);
        out = A + ((B - C) / 2); 
    end
end

function [out] = predictor6(image,line,col)
    %Predictor 6 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        A = image(line,col-1);
        B = image(line-1,col);
        C = image(line-1,col-1);
        out = B + ((A - C) / 2); 
    end
end

function [out] = predictor7(image,line,col)
    %Predictor 7 of JPEG
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        A = image(line,col-1);
        B = image(line-1,col);
        out = (A + B) / 2; 
    end
end

function [out] = predictor8(image,line,col)
    %Predictor JPEG-LS
    if(line == 1 || col == 1)
        out = image(line,col);
    else
        A = image(line,col-1);
        B = image(line-1,col);
        C = image(line-1,col-1);
        if(C >= max(A,B)) 
            out = min(A,B);
        else
            if(C <= min(A,B)) 
                out = max(A,B);
            else
                out = A + B - C; 
            end
        end
    end
end


