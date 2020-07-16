import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { CategorieModelLettreReponseComponent } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse.component';
import { CategorieModelLettreReponseService } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse.service';
import { CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

describe('Component Tests', () => {
  describe('CategorieModelLettreReponse Management Component', () => {
    let comp: CategorieModelLettreReponseComponent;
    let fixture: ComponentFixture<CategorieModelLettreReponseComponent>;
    let service: CategorieModelLettreReponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CategorieModelLettreReponseComponent],
      })
        .overrideTemplate(CategorieModelLettreReponseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieModelLettreReponseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieModelLettreReponseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategorieModelLettreReponse(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categorieModelLettreReponses && comp.categorieModelLettreReponses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
