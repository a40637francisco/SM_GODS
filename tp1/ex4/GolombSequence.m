function [seq] = GolombSequence(nrOfSymbols,M)

seq = cell(1,nrOfSymbols);
% Variables 
q = 0;
r = 0;
low = 2.^floor(log2(M));
high = 2.^ceil(log2(M));
firstValues = high - M;
unary = 0;

for nr = 0 : nrOfSymbols
    if(rem(nr,M) == 0 && nr ~= 0)
        q = q + 1; % Só progride de acordo com o tamanho de M
        r = 0; % Dá reset em cada volta de acordo com o tamanho de M
        unary = 2.^(q+1) - 2;
    end
  golomb = 0;
  if( r < firstValues )
    % Resto não é somado a nada. 
    % Concatena-se com o unário e represena-se a 2bits
    temp = binary2vector(unary);
    y = de2bi(r,floor(log2(M)),'left-msb');
    %y = binary2vector(r,floor(log2(M)));
    golomb = [temp y];
  else
    % Resto é somado a high - M
    x = r + high - M;
    temp = binary2vector(unary);
    y = de2bi(x,ceil(log2(M)),'left-msb');
    %y = binary2vector(x,ceil(log2(M)));
    golomb = [temp y];
  end
    
  seq{1,nr+1} = golomb;
  r = r + 1;
end

end