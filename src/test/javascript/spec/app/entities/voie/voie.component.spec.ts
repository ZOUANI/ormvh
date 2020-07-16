import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { VoieComponent } from 'app/entities/voie/voie.component';
import { VoieService } from 'app/entities/voie/voie.service';
import { Voie } from 'app/shared/model/voie.model';

describe('Component Tests', () => {
  describe('Voie Management Component', () => {
    let comp: VoieComponent;
    let fixture: ComponentFixture<VoieComponent>;
    let service: VoieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [VoieComponent],
      })
        .overrideTemplate(VoieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Voie(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.voies && comp.voies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
