function res = h(i_star, i, lambda)
   dif = i - i_star;
   res = exp(-norm(dif)^2/(lambda*lambda));
end    