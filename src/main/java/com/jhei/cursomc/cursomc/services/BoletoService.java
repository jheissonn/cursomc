package com.jhei.cursomc.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.PagamentoComBoleto;


@Service
public class BoletoService {

	public void PreencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanceDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanceDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}

