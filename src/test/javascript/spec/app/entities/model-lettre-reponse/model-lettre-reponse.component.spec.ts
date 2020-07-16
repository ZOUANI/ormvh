import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { ModelLettreReponseComponent } from 'app/entities/model-lettre-reponse/model-lettre-reponse.component';
import { ModelLettreReponseService } from 'app/entities/model-lettre-reponse/model-lettre-reponse.service';
import { ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';

describe('Component Tests', () => {
  describe('ModelLettreReponse Management Component', () => {
    let comp: ModelLettreReponseComponent;
    let fixture: ComponentFixture<ModelLettreReponseComponent>;
    let service: ModelLettreReponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ModelLettreReponseComponent],
      })
        .overrideTemplate(ModelLettreReponseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModelLettreReponseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModelLettreReponseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ModelLettreReponse(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.modelLettreReponses && comp.modelLettreReponses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
