import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { ModelLettreReponseDetailComponent } from 'app/entities/model-lettre-reponse/model-lettre-reponse-detail.component';
import { ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';

describe('Component Tests', () => {
  describe('ModelLettreReponse Management Detail Component', () => {
    let comp: ModelLettreReponseDetailComponent;
    let fixture: ComponentFixture<ModelLettreReponseDetailComponent>;
    const route = ({ data: of({ modelLettreReponse: new ModelLettreReponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ModelLettreReponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ModelLettreReponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModelLettreReponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modelLettreReponse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modelLettreReponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
