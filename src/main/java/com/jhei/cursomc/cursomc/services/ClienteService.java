package com.jhei.cursomc.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jhei.cursomc.cursomc.domain.Cidade;
import com.jhei.cursomc.cursomc.domain.Cliente;
import com.jhei.cursomc.cursomc.domain.Endereco;
import com.jhei.cursomc.cursomc.domain.enums.Perfil;
import com.jhei.cursomc.cursomc.domain.enums.TipoCliente;
import com.jhei.cursomc.cursomc.dto.ClienteDTO;
import com.jhei.cursomc.cursomc.dto.ClienteNewDTO;
import com.jhei.cursomc.cursomc.repositories.CidadeRepository;
import com.jhei.cursomc.cursomc.repositories.ClienteRepository;
import com.jhei.cursomc.cursomc.repositories.EnderecoRepository;
import com.jhei.cursomc.cursomc.security.UserSS;
import com.jhei.cursomc.cursomc.services.exceptions.AuthorizationException;
import com.jhei.cursomc.cursomc.services.exceptions.DataIntegrityException;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository repoCidade;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private BCryptPasswordEncoder encoderPassword;
	@Autowired
	private S3Service s3Service;
	
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id + ", Tipo" + Cliente.class.getName()));
	}
	
	public List<Cliente> find() {
		return repo.findAll();
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		endRepo.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newOb = find(obj.getId());
		updateData(newOb, obj);
		return repo.save(newOb);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
	    repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque existem entidades relacionadas.");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}  
	
	public Cliente fromDto(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(),null , null, null);
	}
	
	public Cliente fromDto(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(),obj.getCpfoucnpj() , TipoCliente.toEnum(obj.getTipo()), encoderPassword.encode(obj.getSenha()));
		Optional<Cidade> cid = repoCidade.findById(obj.getCidadeId());
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
				obj.getBairro(), obj.getCep(), cli, cid.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!")));
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		if(obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		if(obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		return s3Service.uploadFile(multipartFile);
	}
}

