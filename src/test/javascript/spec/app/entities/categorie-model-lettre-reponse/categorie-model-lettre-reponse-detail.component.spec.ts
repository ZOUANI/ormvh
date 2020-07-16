import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { CategorieModelLettreReponseDetailComponent } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse-detail.component';
import { CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

describe('Component Tests', () => {
  describe('CategorieModelLettreReponse Management Detail Component', () => {
    let comp: CategorieModelLettreReponseDetailComponent;
    let fixture: ComponentFixture<CategorieModelLettreReponseDetailComponent>;
    const route = ({ data: of({ categorieModelLettreReponse: new CategorieModelLettreReponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CategorieModelLettreReponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategorieModelLettreReponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieModelLettreReponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieModelLettreReponse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieModelLettreReponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
