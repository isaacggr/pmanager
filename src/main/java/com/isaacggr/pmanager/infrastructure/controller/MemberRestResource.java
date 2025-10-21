package com.isaacggr.pmanager.infrastructure.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.isaacggr.pmanager.infrastructure.controller.RestConstants.PATH_MEMBERS;
import com.isaacggr.pmanager.domain.applicationservice.MemberService;
import com.isaacggr.pmanager.infrastructure.dto.MemberDTO;
import com.isaacggr.pmanager.infrastructure.dto.SaveMemberDataDTO;

import jakarta.validation.Valid;
import com.isaacggr.pmanager.domain.entity.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(PATH_MEMBERS)
@RequiredArgsConstructor
public class MemberRestResource {

  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<MemberDTO> createMember(@RequestBody @Valid SaveMemberDataDTO saveMemberData) {
    Member member = memberService.createMember(saveMemberData);

    return ResponseEntity
        .created(URI.create(PATH_MEMBERS + "/" + member.getId()))
        .body(MemberDTO.create(member));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MemberDTO> loadMemberById(@PathVariable("id") String memberId) {
    Member member = memberService.loadMemberById(memberId);
    return ResponseEntity.ok(MemberDTO.create(member));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable("id") String memberId) {
    memberService.deleteMember(memberId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<MemberDTO> updateMember(
    @PathVariable("id") String memberId,
    @Valid @RequestBody SaveMemberDataDTO saveMemberData
    ) {
    Member member = memberService.updateMember(memberId, saveMemberData);
    return ResponseEntity.ok(MemberDTO.create(member));
  }  

  @GetMapping
  public ResponseEntity<List<MemberDTO>> findMembers(
   @RequestParam(value ="email", required = false) String email 
  ){
    List<Member> members = memberService.findMembers(email);
    return ResponseEntity.ok(
      members.stream().map(MemberDTO::create).toList()
    );
  }
}
