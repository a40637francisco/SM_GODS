function ex4( file , M )
  
    f = dir(file);
    size = f.bytes;

   [~,nrOfSymbols] = histFile(file);
   % Retirar os simbolos que ocorrem 0 vezes
   
   %get golomb sequence
   seq = GolombSequence(nrOfSymbols,M);
   
   vector = file2Vector(file);
   relFreq = tabulate(vector);
   
   [values,order] = sort(relFreq(:,3),'descend');
   sortedRelFreq = relFreq(order,:);
   
   fid = fopen( 'results.txt', 'wt' );
   nbits = 0;
   for i = 1 : length(vector)
       x = vector(i,1);
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
       disp(i);
   end
   
   fclose(fid);
   total_bytes = nbits / 8;
   disp(nbits);
   print('bytes')
   disp(total_bytes);
   print('compression ratio')
   disp((total_bytes / size)*100);
   
end

